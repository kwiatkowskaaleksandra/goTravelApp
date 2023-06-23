package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Country repository.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
