package biuropodrozy.gotravel.reservationTypeOfRoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReservationsTypeOfRoomServiceImplTest {

    @Mock private ReservationsTypeOfRoomRepository reservationsTypeOfRoomRepository;
    private ReservationsTypeOfRoomServiceImpl reservationsTypeOfRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationsTypeOfRoomService = new ReservationsTypeOfRoomServiceImpl(reservationsTypeOfRoomRepository);
    }

    @Test
    void saveReservationsTypeOfRoom() {
        ReservationsTypeOfRoom reservationsTypeOfRoom = new ReservationsTypeOfRoom();
        reservationsTypeOfRoomService.saveReservationsTypeOfRoom(reservationsTypeOfRoom);
        verify(reservationsTypeOfRoomRepository).save(any(ReservationsTypeOfRoom.class));
    }

    @Test
    void findByReservation_IdReservation() {
        Long idReservation = 1L;
        List<ReservationsTypeOfRoom> expectedList = new ArrayList<>();
        expectedList.add(new ReservationsTypeOfRoom());

        when(reservationsTypeOfRoomRepository.findByReservation_IdReservation(idReservation)).thenReturn(expectedList);

        List<ReservationsTypeOfRoom> resultList = reservationsTypeOfRoomService.findByReservation_IdReservation(idReservation);

        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertEquals(expectedList, resultList);
        verify(reservationsTypeOfRoomRepository).findByReservation_IdReservation(idReservation);
    }

    @Test
    void deleteReservationsTypeOfRoom() {
        ReservationsTypeOfRoom reservationsTypeOfRoom = new ReservationsTypeOfRoom();
        reservationsTypeOfRoomService.deleteReservationsTypeOfRoom(reservationsTypeOfRoom);
        verify(reservationsTypeOfRoomRepository).delete(reservationsTypeOfRoom);
    }
}