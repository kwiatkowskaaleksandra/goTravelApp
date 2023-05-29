package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Attraction;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    List<Attraction> getAllByTrips_idTrip(Long tripsIdTrip);

    List<Attraction> getAll();

    Optional<Attraction> getAttractionByNameAttraction(String nameAttractions);

    List<Attraction> getByOwnOffers_idOwnOffer(Long ownOffersIdOwnOffer);

}
