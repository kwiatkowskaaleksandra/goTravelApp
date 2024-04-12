package biuropodrozy.gotravel.attraction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of the {@link AttractionService} interface.
 */
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    /**
     * The AttractionRepository instance used for accessing and manipulating attraction data.
     */
    private final AttractionRepository attractionRepository;

    @Override
    public List<Attraction> getAllByTrips_idTrip(final Long tripsIdTrip) {
        return attractionRepository.findAllByTrips_idTrip(tripsIdTrip);
    }

    @Override
    public List<Attraction> getAll() {
        return attractionRepository.findAll();
    }

    @Override
    public Attraction getAttractionById(int id) {
        return attractionRepository.findByIdAttraction(id);
    }

}
