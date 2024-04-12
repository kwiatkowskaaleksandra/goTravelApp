package biuropodrozy.gotravel.reservation;

import biuropodrozy.gotravel.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Reservations service.
 */
public interface ReservationService {

    /**
     * Saves a new reservation.
     * This method saves a new reservation to the database, performs validation, calculates the total price,
     * and sends a confirmation email to the user.
     *
     * @param reservation The reservation object to be saved.
     * @param user The user making the reservation.
     * @return The ID of the saved reservation.
     */
    long saveReservation(Reservation reservation, User user);

    /**
     * Updates the payment status of the reservation with the specified ID.
     * This method retrieves the reservation from the repository using the provided ID,
     * sets the payment status to true, and saves the updated reservation back to the repository.
     *
     * @param idReservation The ID of the reservation whose payment status is to be updated.
     */
    void updatePaymentStatus(long idReservation);

    /**
     * Retrieves a list of active reservations for the specified user based on the given period.
     *
     * @param user    the user for whom to retrieve reservations
     * @param period  the period for filtering reservations ("activeOrders" for future departures, "purchasedTrips" for past departures)
     * @return a list of reservations matching the specified criteria
     */
    List<Reservation> getReservationActiveOrders(User user, String period);

    /**
     * Deletes the reservation with the specified ID. This method first retrieves the reservation from the repository
     * using the provided ID, deletes associated reservations type of rooms, and then deletes the reservation itself.
     *
     * @param idReservation The ID of the reservation to be deleted.
     */
    void deleteReservation(Long idReservation);

    /**
     * Generates and retrieves the invoice for the reservation with the specified ID.
     *
     * @param idReservation The ID of the reservation for which to generate the invoice.
     * @return A byte array representing the generated invoice.
     */
    byte[] getReservationInvoice(Long idReservation);

    /**
     * Retrieves a page of active the reservations that have not been accepted.
     *
     * @param pageable the pagination information
     * @return a page of active reservations not accepted
     */
    Page<Reservation> getAllActiveReservationNotAccepted(Pageable pageable);

    /**
     * Changes the acceptance status of the reservation.
     *
     * @param idReservation    the ID of the reservation
     * @param acceptStatus  the new acceptance status
     */
    void changeAcceptStatus(Long idReservation, String acceptStatus);
}
