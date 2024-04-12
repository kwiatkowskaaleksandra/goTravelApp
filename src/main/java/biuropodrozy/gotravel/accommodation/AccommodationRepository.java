package biuropodrozy.gotravel.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Accommodation repository.
 */
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {

    /**
     * Find accommodation by id of the accommodation.
     *
     * @param idAccommodation the id accommodation
     * @return the accommodation
     */
    Accommodation findByIdAccommodation(int idAccommodation);
}
