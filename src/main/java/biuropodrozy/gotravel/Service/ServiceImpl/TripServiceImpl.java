package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Transport;
import biuropodrozy.gotravel.Model.Trip;
import biuropodrozy.gotravel.Repository.TripRepository;
import biuropodrozy.gotravel.Service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;


    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getTripByIdTrip(Long idTrip) {
        return tripRepository.findByIdTrip(idTrip);
    }

    @Override
    public List<Trip> getTripsByTypeOfTrip(String typeOfTrip) {
        return tripRepository.findAllByTypeOfTrip(typeOfTrip);
    }

    @Override
    public List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountry(String typeOfTrip, int idCountry) {
        return tripRepository.findByTypeOfTripAndTripCity_Country_IdCountry(typeOfTrip, idCountry);
    }

    @Override
    public List<Trip> getTripsByTripCity_Country_IdCountry(int idCountry) {
        return tripRepository.findByTripCity_Country_IdCountry(idCountry);
    }

    @Override
    public List<Trip> getTripsByTypeOfTripAndTripTransport(String typeOfTrip, Optional<Transport> tripTransport) {
        return tripRepository.findByTypeOfTripAndTripTransport(typeOfTrip, tripTransport);
    }

    @Override
    public List<Trip> getTripsByTripTransport(Optional<Transport> tripTransport) {
        return tripRepository.findByTripTransport(tripTransport);
    }

    @Override
    public List<Trip> getTripsByTypeOfTripAndPriceBetween(String typeOfTrip, double minPrice, double maxPrice) {
        return tripRepository.findByTypeOfTripAndPriceBetween(typeOfTrip, minPrice, maxPrice);
    }

    @Override
    public List<Trip> getTripsByTypeOfTripAndNumberOfDaysBetween(String typeOfTrip, int minDays, int maxDays) {
        return tripRepository.findByTypeOfTripAndNumberOfDaysBetween(typeOfTrip, minDays, maxDays);
    }

    @Override
    public List<Trip> getTripsByNumberOfDaysBetween(int minDays, int maxDays) {
        return tripRepository.findByNumberOfDaysBetween(minDays, maxDays);
    }

    @Override
    public List<Trip> getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(int idCountry, Optional<Transport> tripTransport, int minDays, int maxDays) {
        return tripRepository.findByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(idCountry, tripTransport, minDays, maxDays);
    }

    @Override
    public List<Trip> getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(String typeOfTrip, int idCountry, Optional<Transport> tripTransport, double minPrice, double maxPrice, int minDays, int maxDays) {
        return tripRepository.findByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(typeOfTrip, idCountry, tripTransport, minPrice, maxPrice, minDays, maxDays);
    }

}
