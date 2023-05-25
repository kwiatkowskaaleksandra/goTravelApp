package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Transport;
import biuropodrozy.gotravel.Model.Trip;

import java.util.List;
import java.util.Optional;

public interface TripService {

    List<Trip> getAllTrips();

    Trip getTripByIdTrip(Long idTrip);

    List<Trip> getTripsByTypeOfTrip(String typeOfTrip);

    List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountry(String typeOfTrip, int idCountry);

    List<Trip> getTripsByTripCity_Country_IdCountry(int idCountry);

    List<Trip> getTripsByTypeOfTripAndTripTransport(String typeOfTrip, Optional<Transport> tripTransport);

    List<Trip> getTripsByTripTransport(Optional<Transport> tripTransport);

    List<Trip> getTripsByTypeOfTripAndPriceBetween(String typeOfTrip, double minPrice, double maxPrice);

    List<Trip> getTripsByTypeOfTripAndNumberOfDaysBetween(String typeOfTrip, int minDays, int maxDays);

    List<Trip> getTripsByNumberOfDaysBetween(int minDays, int maxDays);

    List<Trip> getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(int idCountry, Optional<Transport> tripTransport, int minDays, int maxDays);

    List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(String typeOfTrip, int idCountry, Optional<Transport> tripTransport, double minPrice, double maxPrice, int minDays, int maxDays);

}
