package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.repository.OwnOfferRepository;
import biuropodrozy.gotravel.service.OwnOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnOfferServiceImpl implements OwnOfferService {

    private final OwnOfferRepository ownOfferRepository;

    @Override
    public OwnOffer saveOwnOffer(OwnOffer ownOffer) {
        return ownOfferRepository.save(ownOffer);
    }

    @Override
    public OwnOffer getOwnOfferByIdOwnOffer(Long idOffer) {
        return ownOfferRepository.findByIdOwnOffer(idOffer);
    }

    @Override
    public OwnOffer getTopByOrderByIdOwnOffer() {
        return ownOfferRepository.findTopByOrderByIdOwnOfferDesc();
    }

    @Override
    public List<OwnOffer> getAllOwnOfferByUsername(String username) {
        return ownOfferRepository.findByUser_Username(username);
    }

    @Override
    public void deleteOwnOffer(OwnOffer ownOffer) {
        ownOfferRepository.delete(ownOffer);
    }
}
