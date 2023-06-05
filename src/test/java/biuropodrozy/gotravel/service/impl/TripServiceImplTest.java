package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @Mock
    private TripRepository tripRepository;
    @InjectMocks
    private TripServiceImpl tripService;
    private Trip trip;
    private Transport transport;

    @BeforeEach
    public void setUp(){
        Country country = new Country();
        country.setIdCountry(1);
        country.setNameCountry("Polska");

        City city = new City();
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
        trip.setTripCity(city);
        trip.setTripTransport(transport);
        trip.setPrice(1200.20);
        trip.setNumberOfDays(12);
    }

    @Test
    void getAllTrips() {
        given(tripRepository.findAll()).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getAllTrips();
        assertThat(tripList.size()).isEqualTo(1);
    }

    @Test
    void getTripByIdTrip() {
        given(tripRepository.findByIdTrip(1L)).willReturn(trip);
        Trip trip1 = tripService.getTripByIdTrip(1L);
        assertEquals(trip, trip1);
    }

    @Test
    void getTripsByTypeOfTrip() {
        given(tripRepository.findAllByTypeOfTrip("last minute")).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTypeOfTrip("last minute");
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTypeOfTripAndTripCity_Country_IdCountry() {
        given(tripRepository.findByTypeOfTripAndTripCity_Country_IdCountry("last minute", 1)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTypeOfTripAndTripCity_Country_IdCountry("last minute", 1);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTripCity_Country_IdCountry() {
        given(tripRepository.findByTripCity_Country_IdCountry(1)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTripCity_Country_IdCountry(1);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTypeOfTripAndTripTransport() {
        given(tripRepository.findByTypeOfTripAndTripTransport("last minute", transport)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTypeOfTripAndTripTransport("last minute", transport);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTripTransport() {
        given(tripRepository.findByTripTransport(transport)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTripTransport(transport);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTypeOfTripAndPriceBetween() {
        given(tripRepository.findByTypeOfTripAndPriceBetween("last minute", 1000, 1500)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTypeOfTripAndPriceBetween("last minute", 1000, 1500);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTypeOfTripAndNumberOfDaysBetween() {
        given(tripRepository.findByTypeOfTripAndNumberOfDaysBetween("last minute", 3, 13)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTypeOfTripAndNumberOfDaysBetween("last minute", 3, 13);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByNumberOfDaysBetween() {
        given(tripRepository.findByNumberOfDaysBetween(6, 13)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByNumberOfDaysBetween(6, 13);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween() {
        given(tripRepository.findByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(1, transport, 5, 14)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(1, transport, 5, 14);
        assertEquals(List.of(trip).size(), tripList.size());
    }

    @Test
    void getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween() {
        given(tripRepository.findByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween("last minute",1, transport, 1200, 1500, 4, 14)).willReturn(List.of(trip));
        List<Trip> tripList = tripService.getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween("last minute",1, transport, 1200, 1500, 4, 14);
        assertEquals(List.of(trip).size(), tripList.size());
    }
}