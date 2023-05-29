package biuropodrozy.gotravel.repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAll();

    Trip findByIdTrip(Long idTrip);

    List<Trip> findAllByTypeOfTrip(String typeOfTrip);

    List<Trip> findByTypeOfTripAndTripCity_Country_IdCountry(String typeOfTrip, int idCountry);

    List<Trip> findByTypeOfTripAndTripTransport(String typeOfTrip, Optional<Transport> tripTransport);

    List<Trip> findByTripCity_Country_IdCountry(int idCountry);

    List<Trip> findByTripTransport(Optional<Transport> tripTransport);

    List<Trip> findByTypeOfTripAndPriceBetween(String typeOfTrip, double minPrice, double maxPrice);

    List<Trip> findByTypeOfTripAndNumberOfDaysBetween(String typeOfTrip, int minDays, int maxDays);

    List<Trip> findByNumberOfDaysBetween(int minDays, int maxDays);

    List<Trip> findByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(int idCountry, Optional<Transport> tripTransport, int minDays, int maxDays);

    List<Trip> findByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(String typeOfTrip, int idCountry, Optional<Transport> tripTransport, double minPrice, double maxPrice, int minDays, int maxDays);

}
