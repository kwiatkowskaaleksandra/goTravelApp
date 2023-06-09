package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.OwnOffer;

import java.util.List;

/**
 * The interface Own offer service.
 */
public interface OwnOfferService {

    /**
     * Save new own offer.
     *
     * @param ownOffer the own offer
     * @return the own offer
     */
    OwnOffer saveOwnOffer(OwnOffer ownOffer);

    /**
     * Get by id own offer.
     *
     * @param idOffer the id own offer
     * @return the own offer
     */
    OwnOffer getOwnOfferByIdOwnOffer(Long idOffer);

    /**
     * Get top by descending order by id own offer.
     *
     * @return own offer
     */
    OwnOffer getTopByOrderByIdOwnOffer();

    /**
     * Find by username.
     *
     * @param username the username
     * @return the own offers
     */
    List<OwnOffer> getAllOwnOfferByUsername(String username);

    /**
     * Delete own offer
     *
     * @param ownOffer the own offer
     */
    void deleteOwnOffer(OwnOffer ownOffer);
}
