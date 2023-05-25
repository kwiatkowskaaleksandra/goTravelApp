package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

    List<Attraction> findAllByTrips_idTrip(Long trips_idTrip);

    Optional<Attraction> findByNameAttraction(String name);

    List<Attraction> findByOwnOffers_idOwnOffer(Long ownOffers_idOwnOffer);

}
