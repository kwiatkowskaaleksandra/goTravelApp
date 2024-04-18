package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.ownOffer.OwnOffer;
import biuropodrozy.gotravel.reservation.Reservation;
import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReservationConfirmationServiceTest {

    @InjectMocks private ReservationConfirmationService reservationConfirmationService;

    @BeforeEach
    void setUp() {
        reservationConfirmationService = new ReservationConfirmationService();
    }

    @Test
    void prepareTemplateData_Reservation() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("TestUser");
        Trip trip = new Trip();
        trip.setNumberOfDays(3);
        Reservation reservation = new Reservation();
        reservation.setDateOfReservation(LocalDate.now());
        reservation.setTrip(trip);
        reservation.setTotalPrice(1000.00);

        Map<String, Object> result = reservationConfirmationService.prepareTemplateData(user, reservation);

        assertEquals(user.getUsername(), result.get("username"));
        assertEquals(reservation.getDepartureDate(), result.get("departureDate"));
        assertEquals(reservation.getTrip().getNumberOfDays(), result.get("numberOfDays"));
        assertEquals(reservation.getTotalPrice(), result.get("price"));
    }

    @Test
    void prepareTemplateData_OwnOffer() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("TestUser");
        Trip trip = new Trip();
        trip.setNumberOfDays(3);
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setDateOfReservation(LocalDate.now());
        ownOffer.setNumberOfDays(5);
        ownOffer.setTotalPrice(1000.00);

        Map<String, Object> result = reservationConfirmationService.prepareTemplateData(user, ownOffer);

        assertEquals(user.getUsername(), result.get("username"));
        assertEquals(ownOffer.getDepartureDate(), result.get("departureDate"));
        assertEquals(ownOffer.getNumberOfDays(), result.get("numberOfDays"));
        assertEquals(ownOffer.getTotalPrice(), result.get("price"));
    }

    @Test
    void prepareTemplateData_ThrowError() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("TestUser");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationConfirmationService.prepareTemplateData(user, new Object()));

        assertEquals("Unsupported context type", exception.getMessage());
    }
}