package biuropodrozy.gotravel.favoriteTrip;

import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.trip.TripService;
import biuropodrozy.gotravel.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link FavoriteTripsService} interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteTripsServiceImpl implements FavoriteTripsService {

    /**
     * Repository for accessing and managing FavoriteTrips entities.
     */
    private final FavoriteTripsRepository favoriteTripsRepository;

    /**
     * Service component for managing trip-related operations.
     */
    private final TripService tripService;

    @Override
    public void addToFavorites(User user, Long idTrip) {
        if (favoriteTripsRepository.findByUserAndTrip_IdTrip(user, idTrip) == null) {
            Trip trip = tripService.getTripByIdTrip(idTrip);
            FavoriteTrips favoriteTrips = new FavoriteTrips();
            favoriteTrips.setUser(user);
            favoriteTrips.setTrip(trip);
            favoriteTripsRepository.save(favoriteTrips);
            log.info("The trip has been added to favorites");
        }
    }

    @Override
    public List<Trip> getFavoritesTrips(User user) {
        List<FavoriteTrips> favoriteTrips = favoriteTripsRepository.findAllByUser(user);
        List<Trip> trips = new ArrayList<>();

        for (FavoriteTrips trip: favoriteTrips) {
            trips.add(trip.getTrip());
        }

        return trips;
    }

    @Override
    public void removeTripFromFavorites(User user, Long idTrip) {
        FavoriteTrips favoriteTrips = favoriteTripsRepository.findByUserAndTrip_IdTrip(user, idTrip);
        favoriteTripsRepository.delete(favoriteTrips);
        log.info("The trip has been removed from favorites");
    }
}
