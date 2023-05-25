package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.OwnOffer;

import java.util.List;

public interface OwnOfferService {

    OwnOffer saveOwnOffer(OwnOffer ownOffer);

    OwnOffer getOwnOfferByIdOwnOffer(Long idOffer);

    OwnOffer getTopByOrderByIdOwnOffer();

    List<OwnOffer> getAllOwnOfferByUsername(String username);

    void deleteOwnOffer(OwnOffer ownOffer);
}
