//package biuropodrozy.gotravel.service.impl;
//
//import biuropodrozy.gotravel.model.*;
//import biuropodrozy.gotravel.repository.TripRepository;
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
//
//@ExtendWith(MockitoExtension.class)
//class TripServiceImplTest {
//
//    @Mock
//    private TripRepository tripRepository;
//    @InjectMocks
//    private TripServiceImpl tripService;
//    private Trip trip;
//    private Transport transport;
//
//    @BeforeEach
//    public void setUp(){
//        Country country = new Country();
//        country.setIdCountry(1);
//        country.setNameCountry("Polska");
//
//        City city = new City();
//        city.setIdCity(1);
//        city.setNameCity("Kielce");
//        city.setCountry(country);
//
//        transport = new Transport();
//        transport.setIdTransport(1);
//        transport.setNameTransport("samolot");
//        transport.setPriceTransport(200.0);
//
//        trip = new Trip();
//        trip.setIdTrip(1L);
//        trip.setTypeOfTrip("last minute");
//        trip.setTripCity(city);
//        trip.setTripTransport(transport);
//        trip.setPrice(1200.20);
//        trip.setNumberOfDays(12);
//    }
//
//    @Test
//    void getAllTrips() {
//        given(tripRepository.findAll()).willReturn(List.of(trip));
//        List<Trip> tripList = tripService.getAllTrips();
//        assertThat(tripList.size()).isEqualTo(1);
//    }
//
//    @Test
//    void getTripByIdTrip() {
//        given(tripRepository.findByIdTrip(1L)).willReturn(trip);
//        Trip trip1 = tripService.getTripByIdTrip(1L);
//        assertEquals(trip, trip1);
//    }
//
//    @Test
//    void getTripsByTypeOfTrip() {
//        given(tripRepository.findAllByTypeOfTrip("last minute")).willReturn(List.of(trip));
//        List<Trip> tripList = tripService.getTripsByTypeOfTrip("last minute");
//        assertEquals(List.of(trip).size(), tripList.size());
//    }
//}