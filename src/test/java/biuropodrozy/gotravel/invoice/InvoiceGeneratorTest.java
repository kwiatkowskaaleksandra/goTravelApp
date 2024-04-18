package biuropodrozy.gotravel.invoice;

import biuropodrozy.gotravel.accommodation.Accommodation;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.country.Country;
import biuropodrozy.gotravel.insurance.Insurance;
import biuropodrozy.gotravel.ownOffer.OwnOffer;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.reservation.Reservation;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoom;
import biuropodrozy.gotravel.typeOfTrip.TypeOfTrip;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class InvoiceGeneratorTest {

    @InjectMocks InvoiceGenerator invoiceGenerator;
    @Mock OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
    @Mock ReservationsTypeOfRoomService reservationsTypeOfRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateInvoiceForReservation() {
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("cruises");
        Country country = new Country();
        country.setNameCountry("czechRepublic");
        Insurance insurance = new Insurance();
        insurance.setName("standard");
        Accommodation accommodation = new Accommodation();
        accommodation.setNameAccommodation("threeStarHotel");
        City city = new City();
        city.setCountry(country);
        city.setNameCity("prague");
        Trip trip = new Trip();
        trip.setTypeOfTrip(typeOfTrip);
        trip.setNumberOfDays(3);
        trip.setPrice(200);
        trip.setFood("supper");
        trip.setTripCity(city);
        trip.setTripAccommodation(accommodation);
        TypeOfRoom typeOfRoom = new TypeOfRoom();
        typeOfRoom.setType("tripleRoom");
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setDateOfReservation(LocalDate.now());
        reservation.setDepartureDate(LocalDate.now());
        reservation.setUser(new User());
        reservation.setTotalPrice(200.00);
        reservation.setTrip(trip);
        reservation.setNumberOfAdults(2);
        reservation.setNumberOfChildren(1);
        reservation.setInsuranceReservation(insurance);
        ReservationsTypeOfRoom reservationsTypeOfRoom = new ReservationsTypeOfRoom(1, reservation, typeOfRoom, 2);

        when(reservationsTypeOfRoomService.findByReservation_IdReservation(anyLong())).thenReturn(List.of(reservationsTypeOfRoom));

        byte[] pdfBytes = invoiceGenerator.generateInvoice(reservation);
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        verify(reservationsTypeOfRoomService).findByReservation_IdReservation(reservation.getIdReservation());
    }

    @Test
    void testGenerateInvoiceForOwnOffer() {
        Country country = new Country();
        country.setNameCountry("czechRepublic");
        City city = new City();
        city.setCountry(country);
        city.setNameCity("prague");
        Insurance insurance = new Insurance();
        insurance.setName("standard");
        Accommodation accommodation = new Accommodation();
        accommodation.setNameAccommodation("threeStarHotel");
        TypeOfRoom typeOfRoom = new TypeOfRoom();
        typeOfRoom.setType("tripleRoom");
        OwnOffer ownOffer = new OwnOffer(1L, LocalDate.now(), 1, 3, LocalDate.now(),
                122.00, true, 4, true, null, city, accommodation, new User(), true, true, null, insurance);
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom(1, ownOffer, typeOfRoom, 1);

        when(ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(anyLong())).thenReturn(List.of(ownOfferTypeOfRoom));

        byte[] pdfBytes = invoiceGenerator.generateInvoice(ownOffer);
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        verify(ownOfferTypeOfRoomService).findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
    }

}