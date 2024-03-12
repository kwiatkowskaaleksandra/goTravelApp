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
     * Updates the payment status of the reservation with the specified ID.
     * This method retrieves the own offer from the repository using the provided ID,
     * sets the payment status to true, and saves the updated reservation back to the repository.
     *
     * @param idReservation The ID of the reservation whose payment status is to be updated.
     */
    void updatePaymentStatus(long idReservation);

    /**
     * Retrieves active reservation orders for a user within the specified period.
     *
     * @param user   The user for whom to retrieve active reservation orders
     * @param period The period for which to retrieve reservation orders (e.g., "current", "future")
     * @return A list of active reservation orders
     */
    List<Reservation> getReservationActiveOrders(User user, String period);

    /**
     * Deletes the reservation with the specified ID.
     *
     * @param idReservation The ID of the reservation to be deleted
     */
    void deleteReservation(Long idReservation);

    /**
     * Retrieves the invoice PDF for the reservation with the specified ID.
     *
     * @param idReservation The ID of the reservation for which to retrieve the invoice
     * @return The invoice PDF as a byte array
     */
    byte[] getReservationInvoice(Long idReservation);

}
