package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.OwnOffer;

import java.util.List;

public interface OwnOfferService {

    OwnOffer saveOwnOffer(OwnOffer ownOffer);

    OwnOffer getOwnOfferByIdOwnOffer(Long idOffer);

    OwnOffer getTopByOrderByIdOwnOffer();

    List<OwnOffer> getAllOwnOfferByUsername(String username);

    void deleteOwnOffer(OwnOffer ownOffer);
}
