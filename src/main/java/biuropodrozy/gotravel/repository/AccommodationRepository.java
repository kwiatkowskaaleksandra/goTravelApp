package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Accommodation repository.
 */
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {

}
