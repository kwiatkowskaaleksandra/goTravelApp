package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Attraction repository.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

    /**
     * Find all by id trip.
     *
     * @param tripsIdTrip the id trip
     * @return list of attractions
     */
    List<Attraction> findAllByTrips_idTrip(Long tripsIdTrip);

    /**
     * Find optional attraction by name of the attraction.
     *
     * @param name the name of the attraction
     * @return the attraction
     */
    Optional<Attraction> findByNameAttraction(String name);

    /**
     * Find by id own offer.
     *
     * @param ownOffersIdOwnOffer the id own offer
     * @return list of attractions
     */
    List<Attraction> findByOwnOffers_idOwnOffer(Long ownOffersIdOwnOffer);

}
