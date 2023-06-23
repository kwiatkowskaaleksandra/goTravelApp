package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Trip;

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
     * Get all by type of trip.
     *
     * @param typeOfTrip the type of trip
     * @return list of trips
     */
    List<Trip> getTripsByTypeOfTrip(String typeOfTrip);

}
