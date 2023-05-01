package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import biuropodrozy.gotravel.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

    List<Attraction> findAllByTrips_idTrip(Long trips_idTrip);

 //   Set<Attraction> findAttractionByTrips(Set<Trip> trips);

  //  Set<Attraction> findAttractionByTrips(Optional<Trip> tripOptional);
}
