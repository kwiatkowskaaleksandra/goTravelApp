//package biuropodrozy.gotravel.service.impl;
//
//import biuropodrozy.gotravel.model.Reservation;
//import biuropodrozy.gotravel.model.User;
//import biuropodrozy.gotravel.repository.ReservationRepository;
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
//class ReservationServiceImplTest {
//
//    @Mock
//    private ReservationRepository reservationRepository;
//    @InjectMocks
////    private ReservationServiceImpl reservationService;
//    private Reservation reservation;
//
//    @BeforeEach
//    public void setUp(){
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("janek12");
//
//        reservation = new Reservation();
//        reservation.setIdReservation(1L);
//        reservation.setUser(user);
//    }
//
//    @Test
//    void saveReservation() {
//        given(reservationRepository.save(reservation)).willReturn(reservation);
//        Reservation offer = reservationService.saveReservation(reservation);
//        assertThat(offer).isNotNull();
//    }
//
//    @Test
//    void getReservationsByIdReservation() {
//        given(reservationRepository.findReservationsByIdReservation(1L)).willReturn(reservation);
//        Reservation offer = reservationService.getReservationsByIdReservation(1L);
//        assertThat(offer).isEqualTo(reservation);
//    }
//
//    @Test
//    void getTopByOrderByIdReservation() {
//        given(reservationRepository.findTopByOrderByIdReservationDesc()).willReturn(reservation);
//        Reservation offer = reservationService.getTopByOrderByIdReservation();
//        assertThat(offer).isEqualTo(reservation);
//    }
//
//    @Test
//    void getReservationByIdUser() {
//        given(reservationRepository.findReservationsByUser_Id(1L)).willReturn(List.of(reservation));
//        List<Reservation> offer = reservationService.getReservationByIdUser(1L);
//        assertEquals(List.of(reservation).size(), offer.size());
//    }
//
//    @Test
//    void deleteReservation() {
//        willDoNothing().given(reservationRepository).delete(reservation);
//        reservationService.deleteReservation(reservation);
//        verify(reservationRepository, times(1)).delete(reservation);
//    }
//}