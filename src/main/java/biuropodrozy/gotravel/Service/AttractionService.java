package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import biuropodrozy.gotravel.Model.Trip;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttractionService {

    List<Attraction> getAllByTrips_idTrip(Long trips_idTrip);

   // Set<Attraction> getAttractionByTrips(Set<Trip> tripOptional);
}
