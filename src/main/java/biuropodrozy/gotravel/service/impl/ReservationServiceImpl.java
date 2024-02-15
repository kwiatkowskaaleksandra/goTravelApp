package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.repository.ReservationRepository;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import biuropodrozy.gotravel.service.impl.validation.ValidateReservationServiceImpl;
import biuropodrozy.gotravel.service.impl.validation.ValidationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * The Reservations service implementation.
 */
@RequiredArgsConstructor
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
     * Saves the provided reservation after validating reservation data and calculating the total price.
     *
     * @param reservation The reservation to be saved.
     * @param user        The user associated with the reservation.
     * @return The id of the booked trip.
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
        return savedReservation.getIdReservation();
    }

    /**
     * Get reservation by id reservation.
     *
     * @param idReservation the id reservation
     * @return the reservation
     */
    @Override
    public Reservation getReservationsByIdReservation(final Long idReservation) {
        return reservationRepository.findReservationsByIdReservation(idReservation);
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
     * Get top by descending order by id reservation.
     *
     * @return the reservation
     */
    @Override
    public Reservation getTopByOrderByIdReservation() {
        return reservationRepository.findTopByOrderByIdReservationDesc();
    }

    /**
     * Get reservation by id user.
     *
     * @param idUser the id user
     * @return list of reservation
     */
    @Override
    public List<Reservation> getReservationByIdUser(final Long idUser) {
        return reservationRepository.findReservationsByUser_Id(idUser);
    }

    /**
     * Delete reservation.
     *
     * @param reservation the reservation
     */
    @Override
    public void deleteReservation(final Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    /**
     * Calculates the total price of the reservation based on the number of adults and children.
     *
     * @param reservation The reservation for which the total price needs to be calculated.
     * @return The total price of the reservation.
     */
    private double calculateReservationPrice(Reservation reservation) {
        return (reservation.getNumberOfAdults() * reservation.getTrip().getPrice()) + (reservation.getNumberOfChildren() * (reservation.getTrip().getPrice() / 2));
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
