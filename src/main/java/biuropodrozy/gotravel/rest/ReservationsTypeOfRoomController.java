package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
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
