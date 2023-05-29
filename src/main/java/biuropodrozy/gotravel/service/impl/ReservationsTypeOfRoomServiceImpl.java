package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.repository.ReservationsTypeOfRoomRepository;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
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

    @Override
    public List<ReservationsTypeOfRoom> findByReservation_IdReservation(Long id) {
        return reservationsTypeOfRoomRepository.findByReservation_IdReservation(id);
    }

    @Override
    public void deleteReservationsTypeOfRoom(ReservationsTypeOfRoom reservationsTypeOfRoom) {
        reservationsTypeOfRoomRepository.delete(reservationsTypeOfRoom);
    }
}
