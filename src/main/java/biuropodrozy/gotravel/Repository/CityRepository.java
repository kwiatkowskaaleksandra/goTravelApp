package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.City;
import biuropodrozy.gotravel.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Override
    List<City> findAll();

    Optional<City> findByCountry(Optional<Country> idCountry);

    Optional<City> findByIdCity(int idCity);
}
