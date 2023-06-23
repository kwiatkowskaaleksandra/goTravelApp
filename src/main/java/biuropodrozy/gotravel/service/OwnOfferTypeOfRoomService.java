package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;

import java.util.List;

/**
 * The interface Own offer type of rooms service.
 */
public interface OwnOfferTypeOfRoomService {

    /**
     * Save new own offer type of rooms.
     *
     * @param ownOfferTypeOfRoom the own offer type of rooms
     * @return the own offer type of rooms
     */
    OwnOfferTypeOfRoom saveOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom);

    /**
     * Find own offer type of rooms by id own offer.
     *
     * @param id the id own offer
     * @return list of own offer type of rooms
     */
    List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long id);

    /**
     * Delete own offer type of rooms.
     *
     * @param ownOfferTypeOfRoom the own offer type of rooms
     */
    void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom);

}
