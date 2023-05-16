package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsTypeOfRoomRepository extends JpaRepository<ReservationsTypeOfRoom,Integer> {

    //ReservationsTypeOfRoom findByReservation_IdReservation(Long id);

    List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id);
}
