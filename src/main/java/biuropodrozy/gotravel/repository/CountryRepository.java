package biuropodrozy.gotravel.repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByIdCountry(int idCountry);

    @Override
    List<Country> findAll();
}