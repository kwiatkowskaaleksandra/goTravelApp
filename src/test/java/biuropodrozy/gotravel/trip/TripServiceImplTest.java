package biuropodrozy.gotravel.trip;

import biuropodrozy.gotravel.accommodation.Accommodation;
import biuropodrozy.gotravel.accommodation.AccommodationService;
import biuropodrozy.gotravel.attraction.Attraction;
import biuropodrozy.gotravel.attraction.AttractionService;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.country.Country;
import biuropodrozy.gotravel.exception.TripManagementException;
import biuropodrozy.gotravel.insurance.Insurance;
import biuropodrozy.gotravel.insurance.InsuranceService;
import biuropodrozy.gotravel.photo.Photo;
import biuropodrozy.gotravel.photo.PhotoService;
import biuropodrozy.gotravel.transport.Transport;
import biuropodrozy.gotravel.transport.TransportService;
import biuropodrozy.gotravel.trip.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.trip.tripRecommendation.FuzzyLogicService;
import biuropodrozy.gotravel.trip.tripRecommendation.TripMatcher;
import biuropodrozy.gotravel.typeOfTrip.TypeOfTrip;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class TripServiceImplTest {

    private TripServiceImpl tripService;
    @Mock private TripRepository tripRepository;
    @Mock private AttractionService attractionService;
    @Mock private PhotoService photoService;
    @Mock private InsuranceService insuranceService;
    @Mock private AccommodationService accommodationService;
    @Mock private TransportService transportService;
    @Mock private FuzzyLogicService fuzzyLogicService;
    @Mock private TripMatcher tripMatcher;
    private Trip trip;
    private Attraction attraction;
    private Accommodation accommodation;
    private Transport transport;
    private Insurance insurance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tripService = new TripServiceImpl(tripRepository, attractionService, photoService, insuranceService, accommodationService, transportService, fuzzyLogicService, tripMatcher);

        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setIdTypeOfTrip(1L);
        typeOfTrip.setName("Last Minute");
        Country country = new Country();
        country.setIdCountry(1);
        City city = new City();
        city.setIdCity(1);
        city.setCountry(country);
        transport = new Transport();
        transport.setIdTransport(1);
        transport.setPriceTransport(90);
        accommodation = new Accommodation();
        accommodation.setIdAccommodation(1);
        accommodation.setPriceAccommodation(100);
        Photo photo = new Photo();
        photo.setIdPhoto(1L);
        photo.setUrlPhoto("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.travelplanet.pl%2Fblog%2Fgdzie-w-gory-w-polsce-sprawdz-nasze-propozycje-i-zaplanuj-urlop%2F&psig=AOvVaw3LZtxLk91HZ2gZWvQB3dxE&ust=1713356100766000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNjw4__axoUDFQAAAAAdAAAAABAE");
        attraction = new Attraction();
        attraction.setIdAttraction(1);
        attraction.setPriceAttraction(80);
        insurance = new Insurance();
        insurance.setIdInsurance(1);
        insurance.setPrice(100);

        trip = new Trip();
        trip.setIdTrip(1L);
        trip.setTypeOfTrip(typeOfTrip);
        trip.setTripCity(city);
        trip.setTripTransport(transport);
        trip.setTripAccommodation(accommodation);
        trip.setFood("Dinner");
        trip.setTripDescription("A trip intended for people who like beaches and swimming.");
        trip.setNumberOfDays(4);
        trip.setTripPhoto(Set.of(photo));
        trip.setTripAttraction(Set.of(attraction));
    }

    @Test
    void getAllTrips() {
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();

        given(tripRepository.findAll()).willReturn(Arrays.asList(trip1, trip2));

        List<Trip> trips = tripService.getAllTrips();

        then(tripRepository).should(times(1)).findAll();

        assertAll("trips",
                () -> assertNotNull(trips),
                () -> assertEquals(2, trips.size()));
    }

    @Test
    void getTripByIdTrip() {
        Long idTrip = 1L;

        given(tripRepository.findByIdTrip(idTrip)).willReturn(trip);

        Trip result = tripService.getTripByIdTrip(idTrip);

        assertEquals(trip, result);
        then(tripRepository).should(times(1)).findByIdTrip(idTrip);
    }

    @Test
    void getTripsByTypeOfTrip() {
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("Last Minute");
        Trip trip1 = new Trip();
        trip1.setTypeOfTrip(typeOfTrip);
        Trip trip2 = new Trip();
        trip2.setTypeOfTrip(typeOfTrip);

        List<Trip> allTrips = new ArrayList<>(List.of(trip1, trip2));
        Pageable pageable = PageRequest.of(0,2);

        when(tripRepository.findAllByTypeOfTrip_Name("Last Minute", pageable)).thenReturn(new PageImpl<>(allTrips));

        Page<Trip> result = tripService.getTripsByTypeOfTrip("Last Minute", pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(allTrips, result.getContent());
        assertEquals(trip1, result.getContent().get(0));
        verify(tripRepository, times(1)).findAllByTypeOfTrip_Name("Last Minute", pageable);
    }

    @Test
    void getTripsByTypeOfTrip_all() {
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("Last Minute");
        Trip trip1 = new Trip();
        trip1.setTypeOfTrip(typeOfTrip);
        Trip trip2 = new Trip();
        trip2.setTypeOfTrip(typeOfTrip);

        List<Trip> allTrips = new ArrayList<>(List.of(trip1, trip2));
        when(tripRepository.findAll()).thenReturn(allTrips);
        Pageable pageable = PageRequest.of(0,2);

        Page<Trip> result = tripService.getTripsByTypeOfTrip("all", pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(allTrips, result.getContent());
        assertEquals(trip1, result.getContent().get(0));
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void countTripByTypeOfTrip_all() {
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("Last Minute");
        Trip trip1 = new Trip();
        trip1.setTypeOfTrip(typeOfTrip);
        Trip trip2 = new Trip();
        trip2.setTypeOfTrip(typeOfTrip);

        List<Trip> allTrips = new ArrayList<>(List.of(trip1, trip2));
        when(tripRepository.findAll()).thenReturn(allTrips);

        int tripCount = tripService.countTripByTypeOfTrip("all");

        assertEquals(allTrips.size(), tripCount);
        verify(tripRepository, times(1)).findAll();
        verify(tripRepository, never()).countTripByTypeOfTrip_Name(anyString());
    }

    @Test
    void countTripByTypeOfTrip() {
        int tripCountExpected = 2;

        when(tripRepository.countTripByTypeOfTrip_Name("Last Minute")).thenReturn(tripCountExpected);

        int tripCount = tripService.countTripByTypeOfTrip("Last Minute");

        assertEquals(tripCountExpected, tripCount);
        verify(tripRepository, never()).findAll();
        verify(tripRepository, times(1)).countTripByTypeOfTrip_Name(anyString());
    }

    @Test
    void filteringTrips() {
        Pageable pageable = PageRequest.of(0,2);
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("Last Minute");
        Country country = new Country();
        country.setIdCountry(1);
        City city = new City();
        city.setIdCity(1);
        city.setCountry(country);
        Transport transport = new Transport();
        transport.setIdTransport(2);

        TripFilteringRequest tripFilteringRequest = new TripFilteringRequest("Last Minute", 1, 2, 100, 5000, 3, 9);
        Trip trip1 = new Trip();
        trip1.setTypeOfTrip(typeOfTrip);
        trip1.setTripCity(city);
        trip1.setTripTransport(transport);
        trip1.setPrice(150);
        trip1.setNumberOfDays(4);
        Trip trip2 = new Trip();
        trip2.setTypeOfTrip(typeOfTrip);
        trip2.setTripCity(city);
        trip2.setTripTransport(transport);
        trip2.setPrice(200);
        trip2.setNumberOfDays(3);

        List<Trip> allTrips = new ArrayList<>(List.of(trip1, trip2));
        when(tripRepository.findAll()).thenReturn(allTrips);

        Page<Trip> result = tripService.filteringTrips(tripFilteringRequest, pageable);

        assertEquals(allTrips, result.getContent());
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void filteringTrips_withoutMaxPriceAndMaxDays() {
        Pageable pageable = PageRequest.of(0,2);
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("Last Minute");
        Country country = new Country();
        country.setIdCountry(1);
        City city = new City();
        city.setIdCity(1);
        city.setCountry(country);
        Transport transport = new Transport();
        transport.setIdTransport(2);

        TripFilteringRequest tripFilteringRequest = new TripFilteringRequest();
        tripFilteringRequest.setMinPrice(20);
        tripFilteringRequest.setMinDays(1);
        Trip trip1 = new Trip();
        trip1.setTypeOfTrip(typeOfTrip);
        trip1.setTripCity(city);
        trip1.setTripTransport(transport);
        trip1.setPrice(150);
        trip1.setNumberOfDays(4);
        Trip trip2 = new Trip();
        trip2.setTypeOfTrip(typeOfTrip);
        trip2.setTripCity(city);
        trip2.setTripTransport(transport);
        trip2.setPrice(200);
        trip2.setNumberOfDays(3);

        List<Trip> allTrips = new ArrayList<>(List.of(trip1, trip2));
        when(tripRepository.findAll()).thenReturn(allTrips);

        Page<Trip> result = tripService.filteringTrips(tripFilteringRequest, pageable);

        assertEquals(allTrips, result.getContent());
        verify(tripRepository, times(1)).findAll();
    }


    @Test
    void tripRecommendation() {
        User user = new User();
        UserTripPreferences userTripPreferences = new UserTripPreferences(1L, 2.0, 4.0, 4.0, 1, 3, user);

        Trip filteredTrip = new Trip();
        filteredTrip.setPrice(500);

        Trip trip1 = new Trip();
        trip1.setPrice(100);
        List<Trip> allTrips = Arrays.asList(trip1, filteredTrip);
        when(tripRepository.findAll()).thenReturn(allTrips);
        when(tripRepository.findFirstByOrderByPriceAsc()).thenReturn(trip1);
        when(tripRepository.findFirstByOrderByPriceDesc()).thenReturn(filteredTrip);
        when(fuzzyLogicService.evaluateUserPreferences(any(UserTripPreferences.class))).thenReturn(0.8);
        when(tripMatcher.matchTripWithScore(any(Trip.class), anyDouble(), anyDouble(), anyDouble(), any(UserTripPreferences.class)))
                .thenReturn(false)
                .thenReturn(true);

        List<Trip> recommendedTrips = tripService.tripRecommendation(userTripPreferences);

        assertEquals(1, recommendedTrips.size());
        assertTrue(recommendedTrips.contains(filteredTrip));

        verify(tripRepository).findAll();
        verify(tripMatcher, times(2)).matchTripWithScore(any(Trip.class), anyDouble(), anyDouble(), anyDouble(), any(UserTripPreferences.class));
    }

    @Test
    void getMostBookedTrips() {
        Trip trip1 = new Trip();
        trip1.setIdTrip(1L);
        Trip trip2 = new Trip();
        trip2.setIdTrip(2L);
        Trip trip3 = new Trip();
        trip3.setIdTrip(3L);
        Trip trip4 = new Trip();
        trip4.setIdTrip(4L);
        Trip trip5 = new Trip();
        trip5.setIdTrip(5L);
        Pageable topFive = PageRequest.of(0, 5);
        List<Object[]> mostBookedTripsResult = new ArrayList<>();
        mostBookedTripsResult.add(new Object[]{trip1, 10L});
        mostBookedTripsResult.add(new Object[]{trip2, 7L});
        mostBookedTripsResult.add(new Object[]{trip3, 10L});
        mostBookedTripsResult.add(new Object[]{trip4, 9L});
        mostBookedTripsResult.add(new Object[]{trip5, 15L});

        when(tripRepository.findTop5MostBookedTrips(topFive)).thenReturn(mostBookedTripsResult);

        List<Trip> mostBookedTrips = tripService.getMostBookedTrips();

        assertEquals(5, mostBookedTrips.size());
        assertEquals(1L, mostBookedTrips.get(0).getIdTrip());
        assertEquals(2L, mostBookedTrips.get(1).getIdTrip());
        assertEquals(3L, mostBookedTrips.get(2).getIdTrip());
        assertEquals(4L, mostBookedTrips.get(3).getIdTrip());
        assertEquals(5L, mostBookedTrips.get(4).getIdTrip());

        verify(tripRepository, times(1)).findTop5MostBookedTrips(topFive);
    }

    @Test
    void saveTrip_successNewTrip() {
        trip.setIdTrip(0L);
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        when(attractionService.getAttractionById(anyInt())).thenReturn(attraction);

        tripService.saveTrip(trip);

        verify(tripRepository).save(any(Trip.class));
        verify(attractionService).getAttractionById(anyInt());

    }

    @Test
    void saveTrip_successEditTrip() {
        trip.setIdTrip(1L);

        when(tripRepository.findByIdTrip(1L)).thenReturn(trip);
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        when(attractionService.getAttractionById(anyInt())).thenReturn(attraction);

        tripService.saveTrip(trip);

        verify(tripRepository, times(1)).findByIdTrip(anyLong());
        verify(tripRepository, times(1)).save(any(Trip.class));
        verify(attractionService, times(1)).getAttractionById(anyInt());

    }

    @Test
    void saveTrip_incorrectTransport() {
        trip.getTripTransport().setIdTransport(0);

        assertThrows(TripManagementException.class, () -> tripService.saveTrip(trip), "incorrectlyEnteredData");
        assertThrows(TripManagementException.class, () -> tripService.validate(trip), "incorrectlyEnteredData");

    }

    @Test
    void saveTrip_incorrectFood() {
        trip.setFood("");

        assertThrows(TripManagementException.class, () -> tripService.saveTrip(trip), "incorrectlyEnteredDataFood");
        assertThrows(TripManagementException.class, () -> tripService.validate(trip), "incorrectlyEnteredDataFood");
    }

    @Test
    void saveTrip_incorrectNumberOfDays() {
        trip.setNumberOfDays(0);

        assertThrows(TripManagementException.class, () -> tripService.saveTrip(trip), "incorrectlyEnteredDataNumberOfDays");
        assertThrows(TripManagementException.class, () -> tripService.validate(trip), "incorrectlyEnteredDataNumberOfDays");
    }

    @Test
    void saveTrip_incorrectTripDescription() {
        trip.setTripDescription("");

        assertThrows(TripManagementException.class, () -> tripService.saveTrip(trip), "incorrectlyEnteredDataDescription");
        assertThrows(TripManagementException.class, () -> tripService.validate(trip), "incorrectlyEnteredDataDescription");
    }

    @Test
    void saveTrip_incorrectPhoto() {
        trip.setTripPhoto(new HashSet<>());

        assertThrows(TripManagementException.class, () -> tripService.saveTrip(trip), "incorrectlyEnteredDataTripPhoto");
        assertThrows(TripManagementException.class, () -> tripService.validate(trip), "incorrectlyEnteredDataTripPhoto");
    }

    @Test
    void validate() {
        when(accommodationService.getAccommodationsById(1)).thenReturn(accommodation);
        when(transportService.getTransportById(1)).thenReturn(transport);
        when(attractionService.getAttractionById(1)).thenReturn(attraction);
        when(insuranceService.getAll()).thenReturn(List.of(insurance));

        double expectedTotalPrice = 1000.0; // baza
        expectedTotalPrice += 100 * 4; // zakwaterowanie
        expectedTotalPrice += 90 * 2; // transport
        expectedTotalPrice += 4 * 70; // jedzenie
        expectedTotalPrice += 80; //atrakcje
        expectedTotalPrice += 100; // ubezpieczenie

        double calculatedPrice = tripService.validate(trip);

        assertEquals(expectedTotalPrice, calculatedPrice);

        verify(accommodationService).getAccommodationsById(1);
        verify(transportService).getTransportById(1);
        verify(attractionService, times(1)).getAttractionById(1);
        verify(insuranceService).getAll();
    }

    @Test
    void deleteTheOffer() {
        Long idTrip = 1L;
        Trip trip = new Trip();
        when(tripRepository.findByIdTrip(idTrip)).thenReturn(trip);

        tripService.deleteTheOffer(idTrip);

        verify(photoService, times(1)).deletePhotoForTrip(trip);
        verify(tripRepository, times(1)).delete(trip);
    }
}