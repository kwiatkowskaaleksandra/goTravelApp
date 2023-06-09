package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Accommodation;
import biuropodrozy.gotravel.repository.AccommodationRepository;
import biuropodrozy.gotravel.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Accommodation service implementation.
 */
@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;

    /**
     * Get all accommodations.
     *
     * @return list of accommodations
     */
    @Override
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }
}
