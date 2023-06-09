package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Reservation type of rooms repository.
 */
@Repository
public interface ReservationsTypeOfRoomRepository extends JpaRepository<ReservationsTypeOfRoom, Integer> {

    /**
     * Find by id reservation.
     *
     * @param id the id reservation
     * @return list of reservation type of rooms
     */
    List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id);
}
