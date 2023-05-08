package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;

public interface ReservationsTypeOfRoomService {

    ReservationsTypeOfRoom saveReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom);

    ReservationsTypeOfRoom getByReservation_IdReservation(Long id);
}
