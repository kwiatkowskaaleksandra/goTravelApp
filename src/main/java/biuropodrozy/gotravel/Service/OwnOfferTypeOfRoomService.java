package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.OwnOfferTypeOfRoom;

import java.util.List;

public interface OwnOfferTypeOfRoomService {

    OwnOfferTypeOfRoom saveOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom);

    List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long id);

    void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom);

}
