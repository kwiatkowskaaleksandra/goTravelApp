package biuropodrozy.gotravel.repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsTypeOfRoomRepository extends JpaRepository<ReservationsTypeOfRoom, Integer> {
    List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id);
}
