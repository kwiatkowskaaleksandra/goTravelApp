package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;

import java.util.List;

/**
 * Service interface for managing favorite trips.
 */
public interface FavoriteTripsService {

    /**
     * Adds a trip to favorites for the specified user.
     *
     * @param user The user adding the trip to favorites.
     * @param idTrip The ID of the trip to add to favorites.
     */
    void addToFavorites(User user, Long idTrip);

    /**
     * Retrieves the favorite trips of the specified user.
     *
     * @param user The user whose favorite trips are to be retrieved.
     * @return A list of favorite trips associated with the specified user.
     */
    List<Trip> getFavoritesTrips(User user);

    /**
     * Removes a trip from favorites for the specified user.
     *
     * @param user The user removing the trip from favorites.
     * @param idTrip The ID of the trip to remove from favorites.
     */
    void removeTripFromFavorites(User user, Long idTrip);
}