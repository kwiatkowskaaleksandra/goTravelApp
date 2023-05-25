package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.OwnOffer;
import biuropodrozy.gotravel.Repository.OwnOfferRepository;
import biuropodrozy.gotravel.Service.OwnOfferService;
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
