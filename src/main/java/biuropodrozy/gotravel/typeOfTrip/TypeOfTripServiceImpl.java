package biuropodrozy.gotravel.typeOfTrip;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TypeOfTripService} interface.
 * Provides methods for managing types of trips.
 */
@Service
@RequiredArgsConstructor
public class TypeOfTripServiceImpl implements TypeOfTripService {

    /**
     * Repository for accessing and managing TypeOfTrip entities.
     */
    private final TypeOfTripRepository typeOfTripRepository;

    @Override
    public List<TypeOfTrip> getAllTypeOfTrips() {
        return typeOfTripRepository.findAll();
    }
}
