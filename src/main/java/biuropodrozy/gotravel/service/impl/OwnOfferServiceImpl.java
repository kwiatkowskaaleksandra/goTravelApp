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
     * TemplateDataStrategy instance for reservation confirmation emails.
     */
    private final TemplateDataStrategy templateDataStrategyReservationConfirmation;

    public OwnOfferServiceImpl(OwnOfferRepository ownOfferRepository, AccommodationService accommodationService,
                               AttractionService attractionService, TypeOfRoomService typeOfRoomService, ValidateReservationServiceImpl validateReservation,
                               OwnOfferTypeOfRoomService ownOfferTypeOfRoomService, MailService mailService, @Qualifier("reservationConfirmation") TemplateDataStrategy templateDataStrategyReservationConfirmation) {
        this.ownOfferRepository = ownOfferRepository;
        this.accommodationService = accommodationService;
        this.attractionService = attractionService;
        this.typeOfRoomService = typeOfRoomService;
        this.validateReservation = validateReservation;
        this.ownOfferTypeOfRoomService = ownOfferTypeOfRoomService;
        this.mailService = mailService;
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
     * Get by id own offer.
     *
     * @param idOffer the id own offer
     * @return the own offer
     */
    @Override
    public OwnOffer getOwnOfferByIdOwnOffer(final Long idOffer) {
        return ownOfferRepository.findByIdOwnOffer(idOffer);
    }

    /**
     * Get top by descending order by id own offer.
     *
     * @return own offer
     */
    @Override
    public OwnOffer getTopByOrderByIdOwnOffer() {
        return ownOfferRepository.findTopByOrderByIdOwnOfferDesc();
    }

    /**
     * Find by username.
     *
     * @param username the username
     * @return the own offers
     */
    @Override
    public List<OwnOffer> getAllOwnOfferByUsername(final String username) {
        return ownOfferRepository.findByUser_Username(username);
    }

    /**
     * Delete own offer.
     *
     * @param ownOffer the own offer
     */
    @Override
    public void deleteOwnOffer(final OwnOffer ownOffer) {
        ownOfferRepository.delete(ownOffer);
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
