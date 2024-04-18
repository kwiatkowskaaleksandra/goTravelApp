package biuropodrozy.gotravel.favoriteTrip;

import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.trip.TripService;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class FavoriteTripsServiceImplTest {

    @Mock private FavoriteTripsRepository favoriteTripsRepository;
    private FavoriteTripsServiceImpl favoriteTripsService;
    @Mock private TripService tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        favoriteTripsService = new FavoriteTripsServiceImpl(favoriteTripsRepository, tripService);
    }

    @Test
    void addToFavorites() {
        User user = new User();
        user.setId(1L);
        Long idTrip = 1L;
        when(favoriteTripsRepository.findByUserAndTrip_IdTrip(user, idTrip)).thenReturn(null);
        Trip trip = new Trip();
        when(tripService.getTripByIdTrip(idTrip)).thenReturn(trip);

        favoriteTripsService.addToFavorites(user, idTrip);

        verify(favoriteTripsRepository).save(any(FavoriteTrips.class));
    }

    @Test
    void getFavoritesTrips() {
        User user = new User();
        user.setId(1L);
        Trip trip1 = new Trip();
        trip1.setIdTrip(1L);
        Trip trip2 = new Trip();
        trip2.setIdTrip(2L);

        FavoriteTrips favoriteTrip1 = new FavoriteTrips(1L, user, trip1);
        FavoriteTrips favoriteTrip2 = new FavoriteTrips(2L, user, trip2);

        List<FavoriteTrips> expectedFavouriteTrips = Arrays.asList(favoriteTrip1, favoriteTrip2);

        given(favoriteTripsRepository.findAllByUser(user)).willReturn(expectedFavouriteTrips);

        List<Trip> expectedTrips = Arrays.asList(trip1, trip2);

        List<Trip> result = favoriteTripsService.getFavoritesTrips(user);

        then(favoriteTripsRepository).should(times(1)).findAllByUser(user);

        assertAll("attractions",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(expectedTrips, result));
    }

    @Test
    void removeTripFromFavorites() {
        User user = new User();
        user.setId(1L);
        Trip trip1 = new Trip();
        trip1.setIdTrip(1L);

        FavoriteTrips favoriteTrip1 = new FavoriteTrips(1L, user, trip1);

        when(favoriteTripsRepository.findByUserAndTrip_IdTrip(user, trip1.getIdTrip())).thenReturn(favoriteTrip1);
        favoriteTripsService.removeTripFromFavorites(user, trip1.getIdTrip());

        verify(favoriteTripsRepository).delete(favoriteTrip1);
    }
}