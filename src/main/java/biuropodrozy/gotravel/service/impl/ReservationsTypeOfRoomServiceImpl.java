package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.repository.ReservationsTypeOfRoomRepository;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Reservation type of room service implementation.
 */
@RequiredArgsConstructor
@Service
public class ReservationsTypeOfRoomServiceImpl implements ReservationsTypeOfRoomService {

    private final ReservationsTypeOfRoomRepository reservationsTypeOfRoomRepository;

    /**
     * Save new reservation type of room
     *
     * @param reservationsTypeOfRoom reservation type of room
     * @return the reservation type of room
     */
    @Override
    public ReservationsTypeOfRoom saveReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom) {
        return reservationsTypeOfRoomRepository.save(reservationsTypeOfRoom);
    }

    /**
     * Get by id reservation.
     *
     * @param id the id reservation
     * @return list of reservation type of rooms
     */
    @Override
    public List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id) {
        return reservationsTypeOfRoomRepository.findByReservation_IdReservation(id);
    }

    /**
     * Delete reservation type of room.
     *
     * @param reservationsTypeOfRoom the reservation type of room
     */
    @Override
    public void deleteReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom) {
        reservationsTypeOfRoomRepository.delete(reservationsTypeOfRoom);
    }
}
