package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Trip repository.
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    /**
     * Find all trips.
     *
     * @return list of trips
     */
    List<Trip> findAll();

    /**
     * Find by id trip.
     *
     * @param idTrip the id trip
     * @return the trip
     */
    Trip findByIdTrip(Long idTrip);

    /**
     * Find all by type of trip.
     *
     * @param typeOfTrip the type of room
     * @return list of trips
     */
    List<Trip> findAllByTypeOfTrip(String typeOfTrip);

    /**
     * Find by type of trip and id country.
     *
     * @param typeOfTrip the type of trip
     * @param idCountry the id country
     * @return list of trips
     */
    List<Trip> findByTypeOfTripAndTripCity_Country_IdCountry(String typeOfTrip, int idCountry);

    /**
     * Find by type of trip and transport.
     *
     * @param typeOfTrip the type of trip
     * @param tripTransport the transport
     * @return list of trips
     */
    List<Trip> findByTypeOfTripAndTripTransport(String typeOfTrip, Transport tripTransport);

    /**
     * Find by id country.
     *
     * @param idCountry the id country
     * @return list of trips
     */
    List<Trip> findByTripCity_Country_IdCountry(int idCountry);

    /**
     * Find by transport.
     *
     * @param tripTransport the transport
     * @return list of trips
     */
    List<Trip> findByTripTransport(Transport tripTransport);

    /**
     * Find by type of trip and price range.
     *
     * @param typeOfTrip the type of trip
     * @param minPrice the minimal price
     * @param maxPrice the maximum price
     * @return list of trips
     */
    List<Trip> findByTypeOfTripAndPriceBetween(String typeOfTrip, double minPrice, double maxPrice);

    /**
     * Find by type of trip and number of days.
     *
     * @param typeOfTrip the type of trip
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> findByTypeOfTripAndNumberOfDaysBetween(String typeOfTrip, int minDays, int maxDays);

    /**
     * Find by number of days.
     *
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> findByNumberOfDaysBetween(int minDays, int maxDays);

    /**
     * Find by id country, transport and number of days.
     *
     * @param idCountry the id country
     * @param tripTransport the transport
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> findByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(int idCountry, Transport tripTransport, int minDays, int maxDays);

    /**
     * Find by type of trip, id country, transport, price range and number of days.
     *
     * @param typeOfTrip the type of trip
     * @param idCountry the id country
     * @param tripTransport the transport
     * @param minPrice the minimal number of days
     * @param maxPrice the maximum price
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    List<Trip> findByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(String typeOfTrip, int idCountry, Transport tripTransport, double minPrice, double maxPrice, int minDays, int maxDays);
}
