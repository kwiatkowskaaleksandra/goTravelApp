package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.repository.TripRepository;
import biuropodrozy.gotravel.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Trip service implementation.
 */
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    /**
     * Get all trips.
     *
     * @return list of trips
     */
    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    /**
     * Get by id trip.
     *
     * @param idTrip the id trip
     * @return thre trip
     */
    @Override
    public Trip getTripByIdTrip(Long idTrip) {
        return tripRepository.findByIdTrip(idTrip);
    }

    /**
     * Get all by type of trip.
     *
     * @param typeOfTrip the type of trip
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTypeOfTrip(String typeOfTrip) {
        return tripRepository.findAllByTypeOfTrip(typeOfTrip);
    }

    /**
     * Get by type of trip and id country.
     *
     * @param typeOfTrip the type of trip
     * @param idCountry the id country
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountry(String typeOfTrip, int idCountry) {
        return tripRepository.findByTypeOfTripAndTripCity_Country_IdCountry(typeOfTrip, idCountry);
    }

    /**
     * Get by id country.
     *
     * @param idCountry the id country
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTripCity_Country_IdCountry(int idCountry) {
        return tripRepository.findByTripCity_Country_IdCountry(idCountry);
    }

    /**
     * Get by type of trip and transport.
     *
     * @param typeOfTrip the type of trip
     * @param tripTransport the transport
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTypeOfTripAndTripTransport(String typeOfTrip, Transport tripTransport) {
        return tripRepository.findByTypeOfTripAndTripTransport(typeOfTrip, tripTransport);
    }

    /**
     * Get by transport.
     *
     * @param tripTransport the transport
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTripTransport(Transport tripTransport) {
        return tripRepository.findByTripTransport(tripTransport);
    }

    /**
     * Get by type of trip and price range.
     *
     * @param typeOfTrip the type of trip
     * @param minPrice the minimal price
     * @param maxPrice the maximum price
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTypeOfTripAndPriceBetween(String typeOfTrip, double minPrice, double maxPrice) {
        return tripRepository.findByTypeOfTripAndPriceBetween(typeOfTrip, minPrice, maxPrice);
    }

    /**
     * Get by type of trip and number of days.
     *
     * @param typeOfTrip the type of trip
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTypeOfTripAndNumberOfDaysBetween(String typeOfTrip, int minDays, int maxDays) {
        return tripRepository.findByTypeOfTripAndNumberOfDaysBetween(typeOfTrip, minDays, maxDays);
    }

    /**
     * Get by number of days.
     *
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByNumberOfDaysBetween(int minDays, int maxDays) {
        return tripRepository.findByNumberOfDaysBetween(minDays, maxDays);
    }

    /**
     * Get by id country, transport and number of days.
     *
     * @param idCountry the id country
     * @param tripTransport the transport
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(int idCountry, Transport tripTransport, int minDays, int maxDays) {
        return tripRepository.findByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(idCountry, tripTransport, minDays, maxDays);
    }

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
    @Override
    public List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(String typeOfTrip, int idCountry, Transport tripTransport, double minPrice, double maxPrice, int minDays, int maxDays) {
        return tripRepository.findByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(typeOfTrip, idCountry, tripTransport, minPrice, maxPrice, minDays, maxDays);
    }

}
