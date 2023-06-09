package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * Find reservation by id user.
     *
     * @param idUser the id user
     * @return list of reservations
     */
    List<Reservation> findReservationsByUser_Id(Long idUser);

    /**
     * Find top by descending order by id reservation.
     *
     * @return the reservation
     */
    Reservation findTopByOrderByIdReservationDesc();
}
