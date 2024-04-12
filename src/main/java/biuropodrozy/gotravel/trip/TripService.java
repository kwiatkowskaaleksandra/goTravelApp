package biuropodrozy.gotravel.trip;

import biuropodrozy.gotravel.trip.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Trip service.
 */
public interface TripService {

    /**
     * Get all trips.
     *
     * @return list of trips
     */
    List<Trip> getAllTrips();

    /**
     * Get by id trip.
     *
     * @param idTrip the id trip
     * @return the trip
     */
    Trip getTripByIdTrip(Long idTrip);

    /**
     * Retrieves trips by type of trip with pagination.
     * If typeOfTrip is "search", returns all trips.
     *
     * @param typeOfTrip The type of trip to filter.
     * @param pageable   Pagination information.
     * @return Page of trips filtered by typeOfTrip.
     */
    Page<Trip> getTripsByTypeOfTrip(String typeOfTrip, Pageable pageable);

    /**
     * Counts the number of trips by type of trip.
     * If typeOfTrip is "search", returns the count of all trips.
     *
     * @param typeOfTrip The type of trip to count.
     * @return The count of trips filtered by typeOfTrip.
     */
    int countTripByTypeOfTrip(String typeOfTrip);

    /**
     * Filters trips based on the provided criteria.
     *
     * @param filteringRequest The filtering criteria for trips.
     * @param pageable         Pagination information.
     * @return Page of filtered trips based on the provided criteria.
     */
    Page<Trip> filteringTrips(TripFilteringRequest filteringRequest, Pageable pageable);

    /**
     * Recommends a list of trips based on the user's trip preferences.
     *
     * @param userPreferences The user's trip preferences based on which the trips will be recommended.
     * @return A list of recommended trips matching the user's preferences.
     */
    List<Trip> tripRecommendation(UserTripPreferences userPreferences);

    /**
     * Retrieves a list of the most booked trips.
     *
     * @return A list of the most booked trips.
     */
    List<Trip> getMostBookedTrips();

    /**
     * Saves a trip.
     *
     * @param trip the trip to be saved
     */
    void saveTrip(Trip trip);

    /**
     * Validates a trip and returns a validation score.
     *
     * @param trip the trip to be validated
     * @return the validation score
     */
    double validate(Trip trip);

    /**
     * Deletes an offer.
     *
     * @param idTrip the ID of the trip to be deleted
     */
    void deleteTheOffer(Long idTrip);

}
