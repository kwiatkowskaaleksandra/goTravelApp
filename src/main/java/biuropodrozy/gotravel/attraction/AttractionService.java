package biuropodrozy.gotravel.attraction;

import java.util.List;

/**
 * The interface Attraction service.
 */
public interface AttractionService {

    /**
     * Get all by id trip.
     *
     * @param tripsIdTrip the id trip
     * @return list of attractions
     */
    List<Attraction> getAllByTrips_idTrip(Long tripsIdTrip);

    /**
     * Get all attractions.
     *
     * @return list of attractions
     */
    List<Attraction> getAll();

    /**
     * Get attraction by id of the attraction.
     *
     * @param id the id of the attraction
     * @return the attraction
     */
    Attraction getAttractionById(int id);

}
