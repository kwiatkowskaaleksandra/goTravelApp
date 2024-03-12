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
     */
    void saveOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom);

    /**
     * Retrieves a list of own offer type of rooms associated with the specified own offer ID.
     *
     * @param idOwnOffer The ID of the own offer for which to retrieve type of rooms
     * @return A list of own offer type of rooms
     */
    List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long idOwnOffer);

    /**
     * Deletes the specified own offer type of room.
     *
     * @param ownOfferTypeOfRoom The own offer type of room to be deleted
     */
    void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom);
}
