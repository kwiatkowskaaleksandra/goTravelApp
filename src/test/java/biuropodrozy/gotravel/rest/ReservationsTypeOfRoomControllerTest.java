package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationsTypeOfRoomControllerTest {

    @Mock
    private ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private TypeOfRoomService typeOfRoomService;
    @InjectMocks
    private ReservationsTypeOfRoomController reservationsTypeOfRoomController;
    private ReservationsTypeOfRoom reservationsTypeOfRoom;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setIdReservation(1L);

        TypeOfRoom typeOfRoom = new TypeOfRoom();
        typeOfRoom.setIdTypeOfRoom(1);
        typeOfRoom.setType("jednoosobowy");
        typeOfRoom.setRoomPrice(200.0);

        reservationsTypeOfRoom = new ReservationsTypeOfRoom();
        reservationsTypeOfRoom.setIdReservationsTypeOfRoom(1);
        reservationsTypeOfRoom.setNumberOfRoom(2);
        reservationsTypeOfRoom.setReservation(reservation);
        reservationsTypeOfRoom.setTypeOfRoom(typeOfRoom);
    }

    @Test
    void createNew() {
        when(reservationsTypeOfRoomService.saveReservationsTypeOfRoom(reservationsTypeOfRoom)).thenReturn(reservationsTypeOfRoom);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(reservation);
        ResponseEntity<ReservationsTypeOfRoom> response = reservationsTypeOfRoomController.createNew(1, reservationsTypeOfRoom);
        ReservationsTypeOfRoom reservationsTypeOfRoom1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assertThat(reservationsTypeOfRoom1).isNotNull();
        assertEquals(status, HttpStatusCode.valueOf(200));
    }
}