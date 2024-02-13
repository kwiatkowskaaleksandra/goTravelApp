package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.User;

import java.util.List;

/**
 * The interface Own offer service.
 */
public interface OwnOfferService {

    /**
     * Saves the provided own offer after validating reservation data.
     *
     * @param ownOffer the own offer
     * @param user the user
     */
    void saveOwnOffer(OwnOffer ownOffer, User user);

    /**
     * Calculates the total price for the given own offer.
     *
     * @param ownOffer the own offer
     * @param user the user
     * @return the total price
     */
    double getTotalPrice(OwnOffer ownOffer, User user);

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
     * Delete own offer.
     *
     * @param ownOffer the own offer
     */
    void deleteOwnOffer(OwnOffer ownOffer);
}
