package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface City repository.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    /**
     * Find cities by id country.
     *
     * @param idCountry the id country
     * @return list of cities
     */
    List<City> findByCountry_IdCountry(int idCountry);

}
