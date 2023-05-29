package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Attraction;
import biuropodrozy.gotravel.repository.AttractionRepository;
import biuropodrozy.gotravel.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public List<Attraction> getAllByTrips_idTrip(Long tripsIdTrip) {
        return attractionRepository.findAllByTrips_idTrip(tripsIdTrip);
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
    public List<Attraction> getByOwnOffers_idOwnOffer(Long ownOffersIdOwnOffer) {
        return attractionRepository.findByOwnOffers_idOwnOffer(ownOffersIdOwnOffer);
    }


}
