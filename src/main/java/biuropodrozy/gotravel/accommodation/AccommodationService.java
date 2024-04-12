package biuropodrozy.gotravel.accommodation;

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

    /**
     * Get accommodation by id of the accommodation.
     *
     * @param id the id of the accommodation
     * @return the accommodation
     */
    Accommodation getAccommodationsById(int id);
}
