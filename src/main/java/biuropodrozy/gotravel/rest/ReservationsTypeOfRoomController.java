package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The type Reservations type of room controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservationsTypOfRooms")
public class ReservationsTypeOfRoomController {

    /**
     * Service for managing reservations of type of room.
     */
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;

    /**
     * Service for managing reservations.
     */
    private final ReservationService reservationService;

    /**
     * Service for managing types of room.
     */
    private final TypeOfRoomService typeOfRoomService;

    /**
     * Create new reservations type of room response entity.
     *
     * @param idTypeOfRoom the id type of room
     * @param reservationsTypeOfRoom the reservation type of room
     * @return the response entity
     */
    @PostMapping("/addReservationsTypOfRooms/{idTypeOfRoom}")
    ResponseEntity<ReservationsTypeOfRoom> createNew(@PathVariable final int idTypeOfRoom,
                                                     @RequestBody final ReservationsTypeOfRoom reservationsTypeOfRoom) {

        Reservation reservation = reservationService.getReservationsByIdReservation(reservationService.getTopByOrderByIdReservation().getIdReservation());
        reservationsTypeOfRoom.setReservation(reservation);

        TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoom(idTypeOfRoom);
        reservationsTypeOfRoom.setTypeOfRoom(typeOfRoom);

        return ResponseEntity.ok(reservationsTypeOfRoomService.saveReservationsTypeOfRoom(reservationsTypeOfRoom));

    }

}
