package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Photo repository.
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    /**
     * Find all by id trip.
     *
     * @param idTrip the id trip
     * @return list of photos
     */
    List<Photo> findAllByTrip_IdTrip(Long idTrip);

}
