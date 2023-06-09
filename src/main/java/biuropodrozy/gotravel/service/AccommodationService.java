package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Accommodation;

import java.util.List;

/**
 * The interface Accommodation service.
 */
public interface AccommodationService {

    /**
     * Get all accommodations.
     *
     * @return list of accommodations
     */
    List<Accommodation> getAllAccommodations();
}
