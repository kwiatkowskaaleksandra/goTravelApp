package biuropodrozy.gotravel.reservationTypeOfRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link ReservationsTypeOfRoomService} interface.
 */
@RequiredArgsConstructor
@Service
public class ReservationsTypeOfRoomServiceImpl implements ReservationsTypeOfRoomService {

    /**
     * Repository interface for managing reservations of type of room.
     */
    private final ReservationsTypeOfRoomRepository reservationsTypeOfRoomRepository;

    @Override
    public void saveReservationsTypeOfRoom(final ReservationsTypeOfRoom reservationsTypeOfRoom) {
        reservationsTypeOfRoomRepository.save(reservationsTypeOfRoom);
    }

    @Override
    public List<ReservationsTypeOfRoom> findByReservation_IdReservation(final Long id) {
        return reservationsTypeOfRoomRepository.findByReservation_IdReservation(id);
    }

    @Override
    public void deleteReservationsTypeOfRoom(final ReservationsTypeOfRoom reservationsTypeOfRoom) {
        reservationsTypeOfRoomRepository.delete(reservationsTypeOfRoom);
    }
}
