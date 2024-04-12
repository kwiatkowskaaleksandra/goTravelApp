package biuropodrozy.gotravel.reservation;

import biuropodrozy.gotravel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Reservation repository.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Find reservation by id reservation.
     *
     * @param idReservation the id reservation
     * @return the reservation
     */
    Reservation findReservationsByIdReservation(Long idReservation);

    /**
     * Retrieves a list of future reservations for a specific user.
     *
     * @param user the user for whom to retrieve future reservations
     * @return a list of future reservations for the specified user
     */
    @Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.departureDate >= CURRENT_DATE")
    List<Reservation> findFutureDeparturesForUser(User user);

    /**
     * Retrieves a list of past reservations for a specific user.
     *
     * @param user the user for whom to retrieve past reservations
     * @return a list of past reservations for the specified user
     */
    @Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.departureDate < CURRENT_DATE")
    List<Reservation> findPastDeparturesForUser(User user);

    @Query("SELECT r FROM Reservation r WHERE r.accepted = FALSE AND r.changedAcceptanceState = FALSE AND r.departureDate > CURRENT_DATE")
    List<Reservation> findAllFutureDeparturesNotAccepted();

}
