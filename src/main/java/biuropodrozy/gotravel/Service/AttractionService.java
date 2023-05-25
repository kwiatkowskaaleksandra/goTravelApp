package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    List<Attraction> getAllByTrips_idTrip(Long trips_idTrip);

    List<Attraction> getAll();

    Optional<Attraction> getAttractionByNameAttraction(String nameAttractions);

    List<Attraction> getByOwnOffers_idOwnOffer(Long ownOffers_idOwnOffer);

}
