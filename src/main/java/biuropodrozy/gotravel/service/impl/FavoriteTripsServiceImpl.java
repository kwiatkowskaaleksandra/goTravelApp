package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.FavoriteTrips;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.FavoriteTripsRepository;
import biuropodrozy.gotravel.service.FavoriteTripsService;
import biuropodrozy.gotravel.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the FavoriteTripsService interface providing methods to manage favorite trips.
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

    /**
     * Adds a trip to favorites for the specified user.
     *
     * @param user The user adding the trip to favorites.
     * @param idTrip The ID of the trip to add to favorites.
     */
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

    /**
     * Retrieves the favorite trips of the specified user.
     *
     * @param user The user whose favorite trips are to be retrieved.
     * @return A list of favorite trips associated with the specified user.
     */
    @Override
    public List<Trip> getFavoritesTrips(User user) {
        List<FavoriteTrips> favoriteTrips = favoriteTripsRepository.findAllByUser(user);
        List<Trip> trips = new ArrayList<>();

        for (FavoriteTrips trip: favoriteTrips) {
            trips.add(trip.getTrip());
        }

        return trips;
    }

    /**
     * Removes a trip from favorites for the specified user.
     *
     * @param user The user removing the trip from favorites.
     * @param idTrip The ID of the trip to remove from favorites.
     */
    @Override
    public void removeTripFromFavorites(User user, Long idTrip) {
        FavoriteTrips favoriteTrips = favoriteTripsRepository.findByUserAndTrip_IdTrip(user, idTrip);
        favoriteTripsRepository.delete(favoriteTrips);
        log.info("The trip has been removed from favorites");
    }
}
