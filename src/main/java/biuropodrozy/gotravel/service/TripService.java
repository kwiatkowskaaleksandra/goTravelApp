package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.rest.dto.request.TripFilteringRequest;
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

}
