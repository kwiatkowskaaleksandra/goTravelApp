package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.repository.OwnOfferRepository;
import biuropodrozy.gotravel.service.OwnOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Own offer service implementation.
 */
@RequiredArgsConstructor
@Service
public class OwnOfferServiceImpl implements OwnOfferService {

    private final OwnOfferRepository ownOfferRepository;

    /**
     * Save new own offer.
     *
     * @param ownOffer the own offer
     * @return the own offer
     */
    @Override
    public OwnOffer saveOwnOffer(OwnOffer ownOffer) {
        return ownOfferRepository.save(ownOffer);
    }

    /**
     * Get by id own offer.
     *
     * @param idOffer the id own offer
     * @return the own offer
     */
    @Override
    public OwnOffer getOwnOfferByIdOwnOffer(Long idOffer) {
        return ownOfferRepository.findByIdOwnOffer(idOffer);
    }

    /**
     * Get top by descending order by id own offer.
     *
     * @return own offer
     */
    @Override
    public OwnOffer getTopByOrderByIdOwnOffer() {
        return ownOfferRepository.findTopByOrderByIdOwnOfferDesc();
    }

    /**
     * Find by username.
     *
     * @param username the username
     * @return the own offers
     */
    @Override
    public List<OwnOffer> getAllOwnOfferByUsername(String username) {
        return ownOfferRepository.findByUser_Username(username);
    }

    /**
     * Delete own offer
     *
     * @param ownOffer the own offer
     */
    @Override
    public void deleteOwnOffer(OwnOffer ownOffer) {
        ownOfferRepository.delete(ownOffer);
    }
}
