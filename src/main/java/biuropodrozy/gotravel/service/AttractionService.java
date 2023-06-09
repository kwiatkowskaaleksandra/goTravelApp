package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Attraction;

import java.util.List;
import java.util.Optional;

/**
 * The interface Attraction service.
 */
public interface AttractionService {

    /**
     * Get all by id trip.
     *
     * @param tripsIdTrip the id trip
     * @return list of attractions
     */
    List<Attraction> getAllByTrips_idTrip(Long tripsIdTrip);

    /**
     * Get all attractions
     *
     * @return list of attractions
     */
    List<Attraction> getAll();

    /**
     * Get optional attraction by name of the attraction.
     *
     * @param nameAttractions the name of the attraction
     * @return the attraction
     */
    Optional<Attraction> getAttractionByNameAttraction(String nameAttractions);

    /**
     * Get by id own offer.
     *
     * @param ownOffersIdOwnOffer the id own offer
     * @return list of attractions
     */
    List<Attraction> getByOwnOffers_idOwnOffer(Long ownOffersIdOwnOffer);

}
