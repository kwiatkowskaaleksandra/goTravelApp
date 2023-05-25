package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;

import java.util.List;

public interface ReservationsTypeOfRoomService {

    ReservationsTypeOfRoom saveReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom);

    List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id);

    void deleteReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom);
}
