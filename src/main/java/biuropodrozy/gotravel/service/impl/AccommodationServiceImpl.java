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

    /**
     * The AccommodationRepository instance used for accessing and manipulating accommodation data.
     */
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

    /**
     * Get accommodation by id of the accommodation.
     *
     * @param id the id of the accommodation
     * @return the accommodation
     */
    @Override
    public Accommodation getAccommodationsById(int id) {
        return accommodationRepository.findByIdAccommodation(id);
    }

}
