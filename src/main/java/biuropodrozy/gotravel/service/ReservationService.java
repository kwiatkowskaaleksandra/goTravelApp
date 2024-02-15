package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.User;

import java.util.List;

/**
 * The interface Reservations service.
 */
public interface ReservationService {

    /**
     * Save reservation.
     *
     * @param reservation the reservation
     * @return the id of the booked trip
     */
    long saveReservation(Reservation reservation, User user);

    /**
     * Get reservation by id reservation.
     *
     * @param idReservation the id reservation
     * @return the reservation
     */
    Reservation getReservationsByIdReservation(Long idReservation);

    /**
     * Updates the payment status of the reservation with the specified ID.
     * This method retrieves the own offer from the repository using the provided ID,
     * sets the payment status to true, and saves the updated reservation back to the repository.
     *
     * @param idReservation The ID of the reservation whose payment status is to be updated.
     */
    void updatePaymentStatus(long idReservation);
    /**
     * Get top by descending order by id reservation.
     *
     * @return the reservation
     */
    Reservation getTopByOrderByIdReservation();

    /**
     * Get reservation by id user.
     *
     * @param idUser the id user
     * @return list of reservation
     */
    List<Reservation> getReservationByIdUser(Long idUser);

    /**
     * Delete reservation.
     *
     * @param reservation the reservation
     */
    void deleteReservation(Reservation reservation);

}
