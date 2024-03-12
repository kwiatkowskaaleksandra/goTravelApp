package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.repository.OwnOfferRepository;
import biuropodrozy.gotravel.service.*;
import biuropodrozy.gotravel.service.impl.validation.ValidateReservationServiceImpl;
import biuropodrozy.gotravel.service.impl.validation.ValidationData;
import biuropodrozy.gotravel.service.mail.MailService;
import biuropodrozy.gotravel.service.mail.TemplateDataStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Own offer service implementation.
 */
@Service
@Slf4j
public class OwnOfferServiceImpl implements OwnOfferService {

    /**
     * Repository for managing own offers.
     */
    private final OwnOfferRepository ownOfferRepository;

    /**
     * Service for managing accommodations.
     */
    private final AccommodationService accommodationService;

    /**
     * Service for managing attractions.
     */
    private final AttractionService attractionService;

    /**
     * Service for managing types of rooms.
     */
    private final TypeOfRoomService typeOfRoomService;

    /**
     * Service for validating reservations.
     */
    private final ValidateReservationServiceImpl validateReservation;

    /**
     * Service for managing own offers' types of rooms.
     */
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    /**
     * Service for sending emails.
     */
    private final MailService mailService;

    /**
     * The service responsible for generating PDF invoices.
     */
    private final InvoiceGenerator invoiceGenerator;

    /**
     * TemplateDataStrategy instance for reservation confirmation emails.
     */
    private final TemplateDataStrategy templateDataStrategyReservationConfirmation;

    /**
     * Constructs a new instance of OwnOfferServiceImpl with the provided dependencies.
     *
     * @param ownOfferRepository                           The repository for managing own offers.
     * @param accommodationService                        The service for managing accommodations.
     * @param attractionService                           The service for managing attractions.
     * @param typeOfRoomService                           The service for managing types of rooms.
     * @param validateReservation                         The service for validating reservations.
     * @param ownOfferTypeOfRoomService                   The service for managing own offer type of rooms.
     * @param mailService                                 The service for sending emails.
     * @param invoiceGenerator                            The generator for creating invoices.
     * @param templateDataStrategyReservationConfirmation The strategy for creating reservation confirmation templates.
     */

    public OwnOfferServiceImpl(OwnOfferRepository ownOfferRepository, AccommodationService accommodationService,
                               AttractionService attractionService, TypeOfRoomService typeOfRoomService, ValidateReservationServiceImpl validateReservation,
                               OwnOfferTypeOfRoomService ownOfferTypeOfRoomService, MailService mailService, InvoiceGenerator invoiceGenerator, @Qualifier("reservationConfirmation") TemplateDataStrategy templateDataStrategyReservationConfirmation) {
        this.ownOfferRepository = ownOfferRepository;
        this.accommodationService = accommodationService;
        this.attractionService = attractionService;
        this.typeOfRoomService = typeOfRoomService;
        this.validateReservation = validateReservation;
        this.ownOfferTypeOfRoomService = ownOfferTypeOfRoomService;
        this.mailService = mailService;
        this.invoiceGenerator = invoiceGenerator;
        this.templateDataStrategyReservationConfirmation = templateDataStrategyReservationConfirmation;
    }

    /**
     * Saves the provided own offer after validating reservation data.
     *
     * @param ownOffer The own offer to be saved.
     * @param user     The user associated with the own offer.
     * @throws ReservationException if the total price of the own offer is less than or equal to zero.
     * @return The id of the booked trip.
     */
    @Override
    public long saveOwnOffer(OwnOffer ownOffer, User user) {
        validateReservationData(ownOffer, user);

        if (ownOffer.getTotalPrice() <= 0) {
            log.error("Price is uncorrected.");
            throw new ReservationException("pleaseTryAgain");
        }
        LocalDate dateOfReservation = LocalDate.now();
        ownOffer.setDateOfReservation(dateOfReservation);
        ownOffer.setUser(user);

        ownOffer.setOfferAttraction(
                ownOffer.getOfferAttraction().stream()
                        .map(attraction -> attractionService.getAttractionById(attraction.getIdAttraction()))
                        .collect(Collectors.toSet())
        );

        OwnOffer savedOwnOffer = ownOfferRepository.save(ownOffer);
        saveOwnOfferTypeOfRoom(savedOwnOffer);
        log.info("The trip has been booked.");

        mailService.sendMail(
                user.getEmail(),
                "Reservation confirmation",
                "reservationConfirmation.ftl",
                templateDataStrategyReservationConfirmation.prepareTemplateData(user, savedOwnOffer)
        );

        return savedOwnOffer.getIdOwnOffer();
    }

    /**
     * Calculates the total price for the given own offer.
     *
     * @param ownOffer The own offer for which the total price needs to be calculated.
     * @param user     The user associated with the own offer.
     * @return The total price of the own offer.
     */
    @Override
    public double getTotalPrice(OwnOffer ownOffer, User user) {
        validateReservationData(ownOffer, user);

        double totalPrice = ownOffer.getOwnOfferTypeOfRooms().stream()
                .mapToDouble(ownOfferTypeOfRoom -> {
                    TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoomByType(ownOfferTypeOfRoom.getTypeOfRoom().getType());
                    return ((accommodationService.getAccommodationsById(ownOffer.getOfferAccommodation().getIdAccommodation()).getPriceAccommodation() + typeOfRoom.getRoomPrice()) * ownOfferTypeOfRoom.getNumberOfRoom()) * ownOffer.getNumberOfDays();
                }).sum();

        totalPrice += ownOffer.getOfferAttraction().stream()
                .mapToDouble(attraction -> attractionService.getAttractionById(attraction.getIdAttraction()).getPriceAttraction())
                .sum();

        if (ownOffer.isFood()) {
            totalPrice += ((ownOffer.getNumberOfChildren() * ownOffer.getNumberOfDays() * 100) + (ownOffer.getNumberOfAdults() * ownOffer.getNumberOfDays() * 200));
        }

        totalPrice += ownOffer.getInsuranceOwnOffer().getPrice();

        return totalPrice;
    }

    /**
     * Updates the payment status of the own offer with the specified ID.
     * This method retrieves the own offer from the repository using the provided ID,
     * sets the payment status to true, and saves the updated own offer back to the repository.
     *
     * @param idOwnOffer The ID of the own offer whose payment status is to be updated.
     */
    @Override
    public void updatePaymentStatus(long idOwnOffer) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);
        ownOffer.setPayment(true);
        ownOfferRepository.save(ownOffer);
    }

    /**
     * Retrieves a list of active orders (future departures) or past orders (departures that have already occurred)
     * for the specified user based on the given period.
     *
     * @param user   The user for whom to retrieve the active or past orders.
     * @param period The period indicating whether to retrieve active orders ("activeOrders") or past orders ("pastOrders").
     * @return A list of own offers representing the active or past orders.
     */

    @Override
    public List<OwnOffer> getOwnOffersActiveOrders(User user, String period) {
        return period.equals("activeOrders") ? ownOfferRepository.findFutureDeparturesForUser(user) : ownOfferRepository.findPastDeparturesForUser(user);
    }

    /**
     * Deletes the own offer with the specified ID, along with associated type of rooms and attractions.
     *
     * @param idOwnOffer The ID of the own offer to be deleted.
     */
    @Override
    public void deleteOwnOffer(final Long idOwnOffer) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);
        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
        ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));
        ownOffer.setOfferAttraction(null);
        ownOfferRepository.save(ownOffer);

        ownOfferRepository.delete(ownOffer);
    }

    /**
     * Generates an invoice for the own offer with the specified ID.
     *
     * @param idOwnOffer The ID of the own offer for which the invoice is to be generated.
     * @return The byte array representing the generated invoice.
     */

    @Override
    public byte[] getReservationInvoice(Long idOwnOffer) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);
        return invoiceGenerator.generateInvoice(ownOffer);
    }

    /**
     * Saves the type of rooms associated with the given own offer.
     *
     * @param ownOffer The own offer for which type of rooms need to be saved.
     */
    private void saveOwnOfferTypeOfRoom(OwnOffer ownOffer) {
        ownOffer.getOwnOfferTypeOfRooms().forEach(ownOfferTypeOfRoom -> {
            TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoomByType(ownOfferTypeOfRoom.getTypeOfRoom().getType());
            OwnOfferTypeOfRoom ownOfferTypeOfRoom1 = new OwnOfferTypeOfRoom();
            ownOfferTypeOfRoom1.setTypeOfRoom(typeOfRoom);
            ownOfferTypeOfRoom1.setOwnOffer(ownOffer);
            ownOfferTypeOfRoom1.setNumberOfRoom(ownOfferTypeOfRoom.getNumberOfRoom());
            ownOfferTypeOfRoomService.saveOwnOfferTypeOfRoom(ownOfferTypeOfRoom1);
        });
        log.info("The rooms are saved.");
    }

    /**
     * Validates the reservation data for the given own offer and user.
     * This method is used to ensure that the reservation data is valid before proceeding.
     *
     * @param ownOffer The own offer containing reservation data.
     * @param user The user for whom the reservation is being made.
     */
    private void validateReservationData(OwnOffer ownOffer, User user) {
        ValidationData validationData = new ValidationData(user, ownOffer.getNumberOfChildren(), ownOffer.getNumberOfAdults(), ownOffer.getDepartureDate(),
                ownOffer.getOfferAccommodation().getIdAccommodation(), ownOffer.getOfferCity().getIdCity(), ownOffer.getNumberOfDays());
        validateReservation.validateReservationData(validationData);
    }
}
