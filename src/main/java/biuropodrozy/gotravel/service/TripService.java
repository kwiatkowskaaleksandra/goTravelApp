package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Transport;
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

    /**
     * Get by type of trip and id country.
     *
     * @param typeOfTrip the type of trip
     * @param idCountry the id country
     * @return list of trips
     */
    List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountry(String typeOfTrip, int idCountry);

    /**
     * Get by id country.
     *
     * @param idCountry the id country
     * @return list of trips
     */
    List<Trip> getTripsByTripCity_Country_IdCountry(int idCountry);

    /**
     * Get by type of trip and transport.
     *
     * @param typeOfTrip the type of trip
     * @param tripTransport the transport
     * @return list of trips
     */
    List<Trip> getTripsByTypeOfTripAndTripTransport(String typeOfTrip, Transport tripTransport);

    /**
     * Get by transport.
     *
     * @param tripTransport the transport
     * @return list of trips
     */
    List<Trip> getTripsByTripTransport(Transport tripTransport);

    /**
     * Get by type of trip and price range.
     *
     * @param typeOfTrip the type of trip
     * @param minPrice the minimal price
     * @param maxPrice the maximum price
     * @return list of trips
     */
    List<Trip> getTripsByTypeOfTripAndPriceBetween(String typeOfTrip, double minPrice, double maxPrice);

    /**
     * Get by type of trip and number of days.
     *
     * @param typeOfTrip the type of trip
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> getTripsByTypeOfTripAndNumberOfDaysBetween(String typeOfTrip, int minDays, int maxDays);

    /**
     * Get by number of days.
     *
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> getTripsByNumberOfDaysBetween(int minDays, int maxDays);

    /**
     * Get by id country, transport and number of days.
     *
     * @param idCountry the id country
     * @param tripTransport the transport
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(int idCountry, Transport tripTransport, int minDays, int maxDays);

    /**
     * Get by type of trip, id country, transport, price range and number of days.
     *
     * @param typeOfTrip the type of trip
     * @param idCountry the id country
     * @param tripTransport the transport
     * @param minPrice the minimal price
     * @param maxPrice the maximum price
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(String typeOfTrip, int idCountry, Transport tripTransport, double minPrice, double maxPrice, int minDays, int maxDays);

}
