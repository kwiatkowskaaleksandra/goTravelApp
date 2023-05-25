package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import biuropodrozy.gotravel.Repository.AttractionRepository;
import biuropodrozy.gotravel.Service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public List<Attraction> getAllByTrips_idTrip(Long trips_idTrip) {
        return attractionRepository.findAllByTrips_idTrip(trips_idTrip);
    }

    @Override
    public List<Attraction> getAll() {
        return attractionRepository.findAll();
    }

    @Override
    public Optional<Attraction> getAttractionByNameAttraction(String name) {
        return attractionRepository.findByNameAttraction(name);
    }

    @Override
    public List<Attraction> getByOwnOffers_idOwnOffer(Long ownOffers_idOwnOffer) {
        return attractionRepository.findByOwnOffers_idOwnOffer(ownOffers_idOwnOffer);
    }


}
