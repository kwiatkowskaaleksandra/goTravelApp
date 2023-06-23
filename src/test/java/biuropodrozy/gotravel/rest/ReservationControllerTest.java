package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;
    @Mock
    private UserService userService;
    @Mock
    private TripService tripService;
    @Mock
    private ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    @InjectMocks
    private ReservationController reservationController;
    private Reservation reservation;
    private User user;
    private Trip trip;


    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1L);
        user.setUsername("krysia1234");

        trip = new Trip();
        trip.setIdTrip(1L);

        reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setTrip(trip);
    }

    @Test
    void getReservationByIdReservation() {
        when(reservationService.getReservationsByIdReservation(1L)).thenReturn(reservation);
        ResponseEntity<Reservation> response = reservationController.getReservationByIdReservation(1L);
        Reservation reservation1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assertEquals(reservation, reservation1);
    }

    @Test
    void getReservationByUser() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(reservationService.getReservationByIdUser(1L)).thenReturn(List.of(reservation));
        ResponseEntity<List<Reservation>> response = reservationController.getReservationByUser("krysia1234");
        List<Reservation> reservation1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assert reservation1 != null;
        assertEquals(List.of(reservation).size(), reservation1.size());
    }

    @Test
    void createReservation() {
        user.setFirstname("Krysia");
        user.setLastname("Nowak");
        user.setEmail("krysia@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setZipCode("12345");
        user.setStreetNumber("12 b");
        user.setPhoneNumber("123456789");

        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        when(reservationService.saveReservation(reservation)).thenReturn(reservation);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(reservation);
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfChildren(12);
        reservation.setDepartureDate(LocalDate.now());
        ResponseEntity<Reservation> response = reservationController.createReservation("krysia1234", 1L, reservation);
        Reservation reservation1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assertThat(reservation1).isNotNull();
    }

    @Test
    void createReservation2() {
        user.setFirstname("Krysia");
        user.setLastname("Nowak");
        user.setEmail("krysia@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setZipCode("12345");
        user.setStreetNumber("12 b");
        user.setPhoneNumber("123456789");
        reservationService.deleteReservation(reservation);
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(null);
        when(reservationService.saveReservation(reservation)).thenReturn(reservation);
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfChildren(12);
        reservation.setDepartureDate(LocalDate.now());
        ResponseEntity<Reservation> response = reservationController.createReservation("krysia1234", 1L, reservation);
        Reservation reservation1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assertThat(reservation1).isNotNull();
    }

    @Test
    void createReservationUserException() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(reservation);
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfChildren(12);
        reservation.setDepartureDate(LocalDate.now());

        UserException exception = assertThrows(UserException.class, () ->{
            ResponseEntity<Reservation> response = reservationController.createReservation("krysia1234", 1L, reservation);
            Reservation reservation1 = response.getBody();
            HttpStatusCode status = response.getStatusCode();
            assertEquals(status, HttpStatusCode.valueOf(200));
            assertThat(reservation1).isNotNull();
        });
        assertEquals("Proszę o uzupełnienie wszytskich danych osobowych.", exception.getMessage());
    }

    @Test
    void createReservationReservationException() {
        user.setFirstname("Krysia");
        user.setLastname("Nowak");
        user.setEmail("krysia@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setZipCode("12345");
        user.setStreetNumber("12 b");
        user.setPhoneNumber("123456789");

        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(reservation);

        ReservationException exception = assertThrows(ReservationException.class, () ->{
            ResponseEntity<Reservation> response = reservationController.createReservation("krysia1234", 1L, reservation);
            Reservation reservation1 = response.getBody();
            HttpStatusCode status = response.getStatusCode();
            assertEquals(status, HttpStatusCode.valueOf(200));
            assertThat(reservation1).isNotNull();
        });
        assertEquals("Proszę uzupełnić inormacje o liczbie osób podróżujących.", exception.getMessage());
    }

    @Test
    void createReservationReservationException2() {
        user.setFirstname("Krysia");
        user.setLastname("Nowak");
        user.setEmail("krysia@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setZipCode("12345");
        user.setStreetNumber("12 b");
        user.setPhoneNumber("123456789");

        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(reservation);
        reservation.setNumberOfChildren(12);

        ReservationException exception = assertThrows(ReservationException.class, () ->{
            ResponseEntity<Reservation> response = reservationController.createReservation("krysia1234", 1L, reservation);
            Reservation reservation1 = response.getBody();
            HttpStatusCode status = response.getStatusCode();
            assertEquals(status, HttpStatusCode.valueOf(200));
            assertThat(reservation1).isNotNull();
        });
        assertEquals("Osoby poniżej 18 roku życia nie mogą podróżować bez dorosłego opiekuna.", exception.getMessage());
    }

    @Test
    void createReservationReservationException3() {
        user.setFirstname("Krysia");
        user.setLastname("Nowak");
        user.setEmail("krysia@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setZipCode("12345");
        user.setStreetNumber("12 b");
        user.setPhoneNumber("123456789");

        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        when(reservationService.getTopByOrderByIdReservation()).thenReturn(reservation);
        reservation.setNumberOfChildren(12);
        reservation.setNumberOfAdults(13);
        reservation.setDepartureDate (LocalDate.of(1970, 1, 1));

        ReservationException exception = assertThrows(ReservationException.class, () ->{
            ResponseEntity<Reservation> response = reservationController.createReservation("krysia1234", 1L, reservation);
            Reservation reservation1 = response.getBody();
            HttpStatusCode status = response.getStatusCode();
            assertEquals(status, HttpStatusCode.valueOf(200));
            assertThat(reservation1).isNotNull();
        });
        assertEquals("Proszę podać datę wyjazdu.", exception.getMessage());
    }

    @Test
    void deleteReservation() {
        ArgumentCaptor<Reservation> argumentCaptor = ArgumentCaptor.forClass(Reservation.class);
        when(reservationService.getReservationsByIdReservation(anyLong())).thenReturn(reservation);
        willDoNothing().given(reservationService).deleteReservation(argumentCaptor.capture());
        ResponseEntity<?> response = reservationController.deleteReservation(1L);
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assertEquals(reservation, argumentCaptor.getValue());
    }
}