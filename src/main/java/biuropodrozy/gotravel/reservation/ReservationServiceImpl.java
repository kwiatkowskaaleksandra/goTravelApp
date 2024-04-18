package biuropodrozy.gotravel.reservation;

import biuropodrozy.gotravel.invoice.InvoiceGenerator;
import biuropodrozy.gotravel.mail.MailService;
import biuropodrozy.gotravel.mail.TemplateDataStrategy;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoomService;
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

/**
 * Implementation of the {@link ReservationService} interface.
 */
@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    /**
     * Repository for managing reservations.
     */
    private final ReservationRepository reservationRepository;

    /**
     * Service for managing types of rooms.
     */
    private final TypeOfRoomService typeOfRoomService;

    /**
     * Service for managing reservations' types of rooms.
     */
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;

    /**
     * Service for validating reservations.
     */
    private final ValidateOfThePurchasedOfferServiceImpl validateReservation;

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
     * Constructs a new ReservationServiceImpl with the specified dependencies.
     *
     * @param reservationRepository                       The repository for managing reservations.
     * @param typeOfRoomService                           The service for managing types of rooms.
     * @param reservationsTypeOfRoomService               The service for managing reservations of types of rooms.
     * @param validateReservation                         The service for validating reservations.
     * @param mailService                                 The service for sending emails.
     * @param invoiceGenerator                            The service for generating invoices.
     * @param templateDataStrategyReservationConfirmation The strategy for preparing template data for reservation confirmation emails.
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository, TypeOfRoomService typeOfRoomService,
                                  ReservationsTypeOfRoomService reservationsTypeOfRoomService, ValidateOfThePurchasedOfferServiceImpl validateReservation,
                                  MailService mailService, InvoiceGenerator invoiceGenerator, @Qualifier("reservationConfirmation") TemplateDataStrategy templateDataStrategyReservationConfirmation) {
        this.reservationRepository = reservationRepository;
        this.typeOfRoomService = typeOfRoomService;
        this.reservationsTypeOfRoomService = reservationsTypeOfRoomService;
        this.validateReservation = validateReservation;
        this.mailService = mailService;
        this.invoiceGenerator = invoiceGenerator;
        this.templateDataStrategyReservationConfirmation = templateDataStrategyReservationConfirmation;
    }

    @Override
    public long saveReservation(final Reservation reservation, User user) {
        ValidationData validationData = new ValidationData(user, reservation.getNumberOfChildren(), reservation.getNumberOfAdults(), reservation.getDepartureDate(),
                reservation.getTrip().getTripAccommodation().getIdAccommodation(), reservation.getTrip().getTripCity().getIdCity(), reservation.getTrip().getNumberOfDays());
        validateReservation.validateReservationData(validationData);

        LocalDate dateOfReservation = LocalDate.now();
        double price = calculateReservationPrice(reservation);

        reservation.setAccepted(false);
        reservation.setChangedAcceptanceState(false);
        reservation.setTotalPrice(price);
        reservation.setDateOfReservation(dateOfReservation);
        reservation.setUser(user);

        Reservation savedReservation = reservationRepository.save(reservation);
        saveReservationsTypeOfRoom(savedReservation);
        log.info("The trip has been booked.");

        mailService.sendMail(
                user.getEmail(),
                "Reservation confirmation",
                "reservationConfirmation.ftl",
                templateDataStrategyReservationConfirmation.prepareTemplateData(user, savedReservation)
        );

        return savedReservation.getIdReservation();
    }

    @Override
    public void updatePaymentStatus(long idReservation) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);
        reservation.setPayment(true);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationActiveOrders(User user, String period) {
        return period.equals("activeOrders") ? reservationRepository.findFutureDeparturesForUser(user) : reservationRepository.findPastDeparturesForUser(user);
    }

    @Override
    public void deleteReservation(final Long idReservation) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);
        List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(idReservation);
        reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));

        reservationRepository.delete(reservation);
    }

    @Override
    public byte[] getReservationInvoice(Long idReservation) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);
        return invoiceGenerator.generateInvoice(reservation);
    }

    @Override
    public Page<Reservation> getAllActiveReservationNotAccepted(Pageable pageable) {
        List<Reservation> getAll = reservationRepository.findAllFutureDeparturesNotAccepted();

        int totalElements = getAll.size();
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int pageIndex = Math.max(0, pageNumber - 1);
        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, totalElements);

        List<Reservation> pageContent = getAll.subList(start, Math.min(end, totalElements));
        return new PageImpl<>(pageContent, PageRequest.of(pageIndex, pageSize), totalElements);
    }

    @Override
    public void changeAcceptStatus(Long idReservation, String acceptStatus) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);

        if (acceptStatus.equals("accept")) {
            reservation.setAccepted(true);
        }
        reservation.setChangedAcceptanceState(true);
        reservationRepository.save(reservation);
        log.info("Reservation status changed.");
    }

    /**
     * Calculates the total price of the reservation based on the number of adults and children.
     *
     * @param reservation The reservation for which the total price needs to be calculated.
     * @return The total price of the reservation.
     */
    private double calculateReservationPrice(Reservation reservation) {
        return (reservation.getNumberOfAdults() * reservation.getTrip().getPrice()) + (reservation.getNumberOfChildren() * (reservation.getTrip().getPrice() / 2) + reservation.getInsuranceReservation().getPrice());
    }

    /**
     * Saves the type of rooms associated with the given reservation.
     *
     * @param reservation The reservation for which type of rooms need to be saved.
     */
    private void saveReservationsTypeOfRoom(Reservation reservation) {
        reservation.getTypeOfRoomReservation().forEach(reservationsTypeOfRoom -> {
            TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoomByType(reservationsTypeOfRoom.getTypeOfRoom().getType());
            ReservationsTypeOfRoom reservationsTypeOfRoom1 = new ReservationsTypeOfRoom();
            reservationsTypeOfRoom1.setTypeOfRoom(typeOfRoom);
            reservationsTypeOfRoom1.setReservation(reservation);
            reservationsTypeOfRoom1.setNumberOfRoom(reservationsTypeOfRoom.getNumberOfRoom());
            reservationsTypeOfRoomService.saveReservationsTypeOfRoom(reservationsTypeOfRoom1);
        });
        log.info("The rooms are saved.");
    }

}
