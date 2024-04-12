package biuropodrozy.gotravel.attraction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Attraction repository.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

    /**
     * Find all by id trip.
     *
     * @param tripsIdTrip the id trip
     * @return list of attractions
     */
    List<Attraction> findAllByTrips_idTrip(Long tripsIdTrip);

    /**
     * Find attraction by id of the attraction.
     *
     * @param id the id attraction
     * @return the attractions
     */
    Attraction findByIdAttraction(int id);

}
