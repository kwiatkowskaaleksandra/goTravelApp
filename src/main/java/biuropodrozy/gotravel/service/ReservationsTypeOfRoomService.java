package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;

import java.util.List;

/**
 * The interface Reservation type of room service.
 */
public interface ReservationsTypeOfRoomService {

    /**
     * Save new reservation type of room
     *
     * @param reservationsTypeOfRoom reservation type of room
     * @return the reservation type of room
     */
    ReservationsTypeOfRoom saveReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom);

    /**
     * Get by id reservation.
     *
     * @param id the id reservation
     * @return list of reservation type of rooms
     */
    List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id);

    /**
     * Delete reservation type of room.
     *
     * @param reservationsTypeOfRoom the reservation type of room
     */
    void deleteReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom);
}
