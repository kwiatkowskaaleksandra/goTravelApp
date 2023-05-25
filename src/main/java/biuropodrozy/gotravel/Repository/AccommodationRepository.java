package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {

    List<Accommodation> findAll();
}
