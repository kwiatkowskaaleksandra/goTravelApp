package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.Repository.ReservationsTypeOfRoomRepository;
import biuropodrozy.gotravel.Service.ReservationsTypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationsTypeOfRoomServiceImpl implements ReservationsTypeOfRoomService {

    private final ReservationsTypeOfRoomRepository reservationsTypeOfRoomRepository;

    @Override
    public ReservationsTypeOfRoom saveReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom) {
        return reservationsTypeOfRoomRepository.save(reservationsTypeOfRoom);
    }

   /* @Override
    public ReservationsTypeOfRoom getByReservation_IdReservation(Long id) {
        return (ReservationsTypeOfRoom) reservationsTypeOfRoomRepository.findByReservation_IdReservation(id);
    }
*/
    @Override
    public List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id) {
        return reservationsTypeOfRoomRepository.findByReservation_IdReservation(id);
    }

    @Override
    public void deleteReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom) {
        reservationsTypeOfRoomRepository.delete(reservationsTypeOfRoom);
    }
}
