package biuropodrozy.gotravel.accommodation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link AccommodationService} interface.
 */
@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    /**
     * The AccommodationRepository instance used for accessing and manipulating accommodation data.
     */
    private final AccommodationRepository accommodationRepository;

    @Override
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    @Override
    public Accommodation getAccommodationsById(int id) {
        return accommodationRepository.findByIdAccommodation(id);
    }

}
