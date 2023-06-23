package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Attraction;
import biuropodrozy.gotravel.repository.AttractionRepository;
import biuropodrozy.gotravel.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The Attraction service implementation.
 */
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    /**
     * The AttractionRepository instance used for accessing and manipulating attraction data.
     */
    private final AttractionRepository attractionRepository;

    /**
     * Get all by id trip.
     *
     * @param tripsIdTrip the id trip
     * @return list of attractions
     */
    @Override
    public List<Attraction> getAllByTrips_idTrip(final Long tripsIdTrip) {
        return attractionRepository.findAllByTrips_idTrip(tripsIdTrip);
    }

    /**
     * Get all attractions.
     *
     * @return list of attractions
     */
    @Override
    public List<Attraction> getAll() {
        return attractionRepository.findAll();
    }


    /**
     * Get optional attraction by name of the attraction.
     *
     * @param name the name of the attraction
     * @return the attraction
     */
    @Override
    public Optional<Attraction> getAttractionByNameAttraction(final String name) {
        return attractionRepository.findByNameAttraction(name);
    }

}
