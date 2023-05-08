package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findReservationsByIdReservation(Long idReservation);

    List<Reservation> findReservationsByUser_Id(Long idUser);

    Reservation findTopByOrderByIdReservationDesc();
}
