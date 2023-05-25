package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Reservation;
import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.Model.TypeOfRoom;
import biuropodrozy.gotravel.Service.ReservationService;
import biuropodrozy.gotravel.Service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.Service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservationsTypOfRooms")
public class ReservationsTypeOfRoomController {

    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    private final ReservationService reservationService;
    private final TypeOfRoomService typeOfRoomService;

    @PostMapping("/addReservationsTypOfRooms/{idTypeOfRoom}")
    ResponseEntity<ReservationsTypeOfRoom> createNew(@PathVariable int idTypeOfRoom, @RequestBody ReservationsTypeOfRoom reservationsTypeOfRoom) {

        Reservation reservation = reservationService.getReservationsByIdReservation(reservationService.getTopByOrderByIdReservation().getIdReservation());
        reservationsTypeOfRoom.setReservation(reservation);

        TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoom(idTypeOfRoom);
        reservationsTypeOfRoom.setTypeOfRoom(typeOfRoom);

        return ResponseEntity.ok(reservationsTypeOfRoomService.saveReservationsTypeOfRoom(reservationsTypeOfRoom));

    }

}
