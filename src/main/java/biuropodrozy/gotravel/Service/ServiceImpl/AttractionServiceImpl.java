package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import biuropodrozy.gotravel.Model.Trip;
import biuropodrozy.gotravel.Repository.AttractionRepository;
import biuropodrozy.gotravel.Service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;



    @Override
    public List<Attraction> getAllByTrips_idTrip(Long trips_idTrip) {
        return attractionRepository.findAllByTrips_idTrip(trips_idTrip);
    }


    //  @Override
 //   public Set<Attraction> getAttractionByTrips(Set<Trip> tripOptional) {
  //      return attractionRepository.findAttractionByTrips(tripOptional);
 //  }
}
