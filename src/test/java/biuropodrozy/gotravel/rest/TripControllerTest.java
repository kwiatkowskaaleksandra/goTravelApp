package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.City;
import biuropodrozy.gotravel.model.Country;
import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.service.TransportService;
import biuropodrozy.gotravel.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripControllerTest {

    @Mock
    private TripService tripService;
    @Mock
    private TransportService transportService;
    @InjectMocks
    private TripController tripController;
    private Trip trip;
    private Country country;
    private City city;
    private Transport transport;


    @BeforeEach
    void setUp() {
        country = new Country();
        country.setIdCountry(1);
        country.setNameCountry("Polska");

        city = new City();
        city.setIdCity(1);
        city.setNameCity("Kielce");
        city.setCountry(country);

        transport = new Transport();
        transport.setIdTransport(1);
        transport.setNameTransport("samolot");
        transport.setPriceTransport(200.0);

        trip = new Trip();
        trip.setIdTrip(1L);
        trip.setTypeOfTrip("last minute");
    }

    @Test
    void readTripById() {
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);
        ResponseEntity<Trip> response = tripController.readTripById(1L);
        Trip trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assertEquals(trip, trip1);
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void readAllTrips() {
        when(tripService.getTripsByTypeOfTrip("last minute")).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.readAllTrips("last minute");
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterByCountryTransportNumberOfDaysWithoutParameters() {
        when(tripService.getAllTrips()).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterByCountryTransportNumberOfDays(0, 0, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterByCountryTransportNumberOfDaysWithCountry() {
        trip.setTripCity(city);
        when(tripService.getTripsByTripCity_Country_IdCountry(1)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterByCountryTransportNumberOfDays(1, 0, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterByCountryTransportNumberOfDaysWithTransport() {
        trip.setTripTransport(transport);
        when(tripService.getTripsByTripTransport(transport)).thenReturn(List.of(trip));
        when(transportService.getTransportById(1)).thenReturn(transport);
        ResponseEntity<List<Trip>> response = tripController.filterByCountryTransportNumberOfDays(0, 1, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterByCountryTransportNumberOfDaysWithNumberOfDaysMin() {
        trip.setNumberOfDays(12);
        when(tripService.getTripsByNumberOfDaysBetween(5,10000)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterByCountryTransportNumberOfDays(0, 0, 5, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterByCountryTransportNumberOfDaysWithNumberOfDaysMax() {
        trip.setNumberOfDays(12);
        when(tripService.getTripsByNumberOfDaysBetween(0,15)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterByCountryTransportNumberOfDays(0, 0, 0, 15);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterByCountryTransportNumberOfDaysWithParameters() {
        trip.setTripCity(city);
        trip.setTripTransport(transport);
        trip.setNumberOfDays(12);
        when(tripService.getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(1, transport, 5,15)).thenReturn(List.of(trip));
        when(transportService.getTransportById(1)).thenReturn(transport);
        ResponseEntity<List<Trip>> response = tripController.filterByCountryTransportNumberOfDays(1, 1, 5, 15);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithoutParameters() {
        when(tripService.getTripsByTypeOfTrip("last minute")).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 0, 0, 0, 0, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithCountry() {
        trip.setTripCity(city);
        when(tripService.getTripsByTypeOfTripAndTripCity_Country_IdCountry("last minute", 1)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 1, 0, 0, 0, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithTransport() {
        trip.setTripTransport(transport);
        when(tripService.getTripsByTypeOfTripAndTripTransport("last minute", transport)).thenReturn(List.of(trip));
        when(transportService.getTransportById(1)).thenReturn(transport);
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 0, 1, 0, 0, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithPriceMin() {
        trip.setPrice(1500);
        when(tripService.getTripsByTypeOfTripAndPriceBetween("last minute", 100,  100000000)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 0, 0, 100.0, 0, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithPriceMax() {
        trip.setPrice(1500);
        when(tripService.getTripsByTypeOfTripAndPriceBetween("last minute", 0,  2000)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 0, 0, 0, 2000, 0, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithNumberOfDaysMin() {
        trip.setNumberOfDays(20);
        when(tripService.getTripsByTypeOfTripAndNumberOfDaysBetween("last minute", 4,  10000)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 0, 0, 0, 0, 4, 0);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithNumberOfDaysMax() {
        trip.setNumberOfDays(20);
        when(tripService.getTripsByTypeOfTripAndNumberOfDaysBetween("last minute", 0,  20)).thenReturn(List.of(trip));
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 0, 0, 0, 0, 0, 20);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void filterTripByTypeOfTripCountryTransportPriceNumberOfDaysWithParameters() {
        trip.setTripCity(city);
        trip.setTripTransport(transport);
        trip.setNumberOfDays(20);
        trip.setPrice(1500);
        when(tripService.getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween("last minute",1,  transport, 100, 2000, 4, 20)).thenReturn(List.of(trip));
        when(transportService.getTransportById(1)).thenReturn(transport);
        ResponseEntity<List<Trip>> response = tripController.filterTripByTypeOfTripCountryTransportPriceNumberOfDays("last minute", 1, 1, 100, 2000, 4, 20);
        List<Trip> trip1 = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        assert trip1 != null;
        assertEquals(List.of(trip).size(), trip1.size());
        assertEquals(status, HttpStatusCode.valueOf(200));
    }
}