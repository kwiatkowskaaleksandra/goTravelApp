package biuropodrozy.gotravel.service.impl;

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

    /**
     * Repository for accessing and managing Trip entities.
     */
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
    public Trip getTripByIdTrip(final Long idTrip) {
        return tripRepository.findByIdTrip(idTrip);
    }

    /**
     * Get all by type of trip.
     *
     * @param typeOfTrip the type of trip
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByTypeOfTrip(final String typeOfTrip) {
        return tripRepository.findAllByTypeOfTrip(typeOfTrip);
    }
}
