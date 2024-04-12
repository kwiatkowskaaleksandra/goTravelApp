package biuropodrozy.gotravel.ownOffer;

import biuropodrozy.gotravel.accommodation.AccommodationService;
import biuropodrozy.gotravel.attraction.Attraction;
import biuropodrozy.gotravel.attraction.AttractionService;
import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.invoice.InvoiceGenerator;
import biuropodrozy.gotravel.mail.MailService;
import biuropodrozy.gotravel.mail.TemplateDataStrategy;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoom;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoomService;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.validation.ValidateOfThePurchasedOfferServiceImpl;
import biuropodrozy.gotravel.validation.ValidationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link OwnOfferService} interface.
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
    private final ValidateOfThePurchasedOfferServiceImpl validateReservation;

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
                               AttractionService attractionService, TypeOfRoomService typeOfRoomService, ValidateOfThePurchasedOfferServiceImpl validateReservation,
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
        ownOffer.setAccepted(false);
        ownOffer.setChangedAcceptanceState(false);

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

    @Override
    public double getTotalPrice(OwnOffer ownOffer, User user) {
        validateReservationData(ownOffer, user);

        double totalPrice = ownOffer.getOwnOfferTypeOfRooms().stream()
                .mapToDouble(ownOfferTypeOfRoom -> {
                    TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoomByType(ownOfferTypeOfRoom.getTypeOfRoom().getType());
                    return ((accommodationService.getAccommodationsById(ownOffer.getOfferAccommodation().getIdAccommodation()).getPriceAccommodation() + typeOfRoom.getRoomPrice()) * ownOfferTypeOfRoom.getNumberOfRoom()) * ownOffer.getNumberOfDays();
                }).sum();

        if (ownOffer.getOfferAttraction().size() > 0) {
            for (Attraction attraction: ownOffer.getOfferAttraction()) {
                totalPrice += attractionService.getAttractionById(attraction.getIdAttraction()).getPriceAttraction();
            }
        }

        if (ownOffer.isFood()) {
            totalPrice += ((ownOffer.getNumberOfChildren() * ownOffer.getNumberOfDays() * 100) + (ownOffer.getNumberOfAdults() * ownOffer.getNumberOfDays() * 200));
        }

        totalPrice += ownOffer.getInsuranceOwnOffer().getPrice();

        return totalPrice;
    }

    @Override
    public void updatePaymentStatus(long idOwnOffer) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);
        ownOffer.setPayment(true);
        ownOfferRepository.save(ownOffer);
    }

    @Override
    public List<OwnOffer> getOwnOffersActiveOrders(User user, String period) {
        return period.equals("activeOrders") ? ownOfferRepository.findFutureDeparturesForUser(user) : ownOfferRepository.findPastDeparturesForUser(user);
    }

    @Override
    public void deleteOwnOffer(final Long idOwnOffer) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);
        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
        ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));
        ownOffer.setOfferAttraction(null);
        ownOfferRepository.save(ownOffer);

        ownOfferRepository.delete(ownOffer);
    }

    @Override
    public byte[] getReservationInvoice(Long idOwnOffer) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);
        return invoiceGenerator.generateInvoice(ownOffer);
    }

    @Override
    public Page<OwnOffer> getAllActiveOwnOffersNotAccepted(Pageable pageable) {
        List<OwnOffer> allOffers = ownOfferRepository.findAllFutureDeparturesNotAccepted();

        int totalElements = allOffers.size();
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int pageIndex = pageNumber - 1;
        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, totalElements);

        List<OwnOffer> pageContent = allOffers.subList(start, Math.min(end, totalElements));
        return new PageImpl<>(pageContent, PageRequest.of(pageIndex, pageSize), totalElements);
    }

    @Override
    public void changeAcceptStatus(Long idOwnOffer, String acceptStatus) {
        OwnOffer ownOffer = ownOfferRepository.findByIdOwnOffer(idOwnOffer);

        if (acceptStatus.equals("accept")) ownOffer.setAccepted(true);

        ownOffer.setChangedAcceptanceState(true);
        ownOfferRepository.save(ownOffer);
        log.info("Own offer reservation status changed.");
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
