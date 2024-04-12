//package biuropodrozy.gotravel.service.impl;
//
//import biuropodrozy.gotravel.reservation.Reservation;
//import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoom;
//import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoomRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class ReservationsTypeOfRoomServiceImplTest {
//    @Mock
//    private ReservationsTypeOfRoomRepository reservationsTypeOfRoomRepository;
//    @InjectMocks
//    private ReservationsTypeOfRoomServiceImpl reservationsTypeOfRoomService;
//    private ReservationsTypeOfRoom reservationsTypeOfRoom;
//
//    @BeforeEach
//    public void setUp(){
//        Reservation reservation = new Reservation();
//        reservation.setIdReservation(1L);
//
//        reservationsTypeOfRoom = new ReservationsTypeOfRoom();
//        reservationsTypeOfRoom.setIdReservationsTypeOfRoom(1);
//        reservationsTypeOfRoom.setReservation(reservation);
//    }
//
//    @Test
//    void saveReservationsTypeOfRoom() {
//        given(reservationsTypeOfRoomRepository.save(reservationsTypeOfRoom)).willReturn(reservationsTypeOfRoom);
//        ReservationsTypeOfRoom room = reservationsTypeOfRoomService.saveReservationsTypeOfRoom(reservationsTypeOfRoom);
//        assertThat(room).isNotNull();
//    }
//
//    @Test
//    void findByReservation_IdReservation() {
//        given(reservationsTypeOfRoomRepository.findByReservation_IdReservation(1L)).willReturn(List.of(reservationsTypeOfRoom));
//        List<ReservationsTypeOfRoom> rooms = reservationsTypeOfRoomService.findByReservation_IdReservation(1L);
//        assertEquals(List.of(reservationsTypeOfRoom).size(), rooms.size());
//    }
//
//    @Test
//    void deleteReservationsTypeOfRoom() {
//        willDoNothing().given(reservationsTypeOfRoomRepository).delete(reservationsTypeOfRoom);
//        reservationsTypeOfRoomService.deleteReservationsTypeOfRoom(reservationsTypeOfRoom);
//        verify(reservationsTypeOfRoomRepository, times(1)).delete(reservationsTypeOfRoom);
//
//    }
//}