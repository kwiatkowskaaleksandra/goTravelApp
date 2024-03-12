package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.repository.ReservationRepository;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import biuropodrozy.gotravel.service.impl.validation.ValidateReservationServiceImpl;
import biuropodrozy.gotravel.service.impl.validation.ValidationData;
import biuropodrozy.gotravel.service.mail.MailService;
import biuropodrozy.gotravel.service.mail.TemplateDataStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * The Reservations service implementation.
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
    private final ValidateReservationServiceImpl validateReservation;

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
                                  ReservationsTypeOfRoomService reservationsTypeOfRoomService, ValidateReservationServiceImpl validateReservation,
                                  MailService mailService, InvoiceGenerator invoiceGenerator, @Qualifier("reservationConfirmation") TemplateDataStrategy templateDataStrategyReservationConfirmation) {
        this.reservationRepository = reservationRepository;
        this.typeOfRoomService = typeOfRoomService;
        this.reservationsTypeOfRoomService = reservationsTypeOfRoomService;
        this.validateReservation = validateReservation;
        this.mailService = mailService;
        this.invoiceGenerator = invoiceGenerator;
        this.templateDataStrategyReservationConfirmation = templateDataStrategyReservationConfirmation;
    }

    /**
     * Saves a new reservation.
     * This method saves a new reservation to the database, performs validation, calculates the total price,
     * and sends a confirmation email to the user.
     *
     * @param reservation The reservation object to be saved.
     * @param user The user making the reservation.
     * @return The ID of the saved reservation.
     */
    @Override
    public long saveReservation(final Reservation reservation, User user) {
        ValidationData validationData = new ValidationData(user, reservation.getNumberOfChildren(), reservation.getNumberOfAdults(), reservation.getDepartureDate(),
                reservation.getTrip().getTripAccommodation().getIdAccommodation(), reservation.getTrip().getTripCity().getIdCity(), reservation.getTrip().getNumberOfDays());
        validateReservation.validateReservationData(validationData);

        LocalDate dateOfReservation = LocalDate.now();
        double price = calculateReservationPrice(reservation);

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

    /**
     * Updates the payment status of the reservation with the specified ID.
     * This method retrieves the reservation from the repository using the provided ID,
     * sets the payment status to true, and saves the updated reservation back to the repository.
     *
     * @param idReservation The ID of the reservation whose payment status is to be updated.
     */
    @Override
    public void updatePaymentStatus(long idReservation) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);
        reservation.setPayment(true);
        reservationRepository.save(reservation);
    }

    /**
     * Retrieves a list of active reservations for the specified user based on the given period.
     *
     * @param user    the user for whom to retrieve reservations
     * @param period  the period for filtering reservations ("activeOrders" for future departures, "pastOrders" for past departures)
     * @return a list of reservations matching the specified criteria
     */
    @Override
    public List<Reservation> getReservationActiveOrders(User user, String period) {
        return period.equals("activeOrders") ? reservationRepository.findFutureDeparturesForUser(user) : reservationRepository.findPastDeparturesForUser(user);
    }

    /**
     * Deletes the reservation with the specified ID. This method first retrieves the reservation from the repository
     * using the provided ID, deletes associated reservations type of rooms, and then deletes the reservation itself.
     *
     * @param idReservation The ID of the reservation to be deleted.
     */
    @Override
    public void deleteReservation(final Long idReservation) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);
        List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(idReservation);
        reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));

        reservationRepository.delete(reservation);
    }

    /**
     * Generates and retrieves the invoice for the reservation with the specified ID.
     *
     * @param idReservation The ID of the reservation for which to generate the invoice.
     * @return A byte array representing the generated invoice.
     */
    @Override
    public byte[] getReservationInvoice(Long idReservation) {
        Reservation reservation = reservationRepository.findReservationsByIdReservation(idReservation);
        return invoiceGenerator.generateInvoice(reservation);
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
