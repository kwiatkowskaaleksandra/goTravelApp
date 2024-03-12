package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.repository.OwnOfferTypeOfRoomRepository;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Own offer type of rooms service implementation.
 */
@RequiredArgsConstructor
@Service
public class OwnOfferTypeOfRoomServiceImpl implements OwnOfferTypeOfRoomService {

    /**
     * Repository interface for managing own offer type of rooms.
     */
    private final OwnOfferTypeOfRoomRepository ownOfferTypeOfRoomRepository;

    /**
     * Save new own offer type of rooms.
     *
     * @param ownOfferTypeOfRoom the own offer type of rooms
     */
    @Override
    public void saveOwnOfferTypeOfRoom(final OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        ownOfferTypeOfRoomRepository.save(ownOfferTypeOfRoom);
    }

    /**
     * Retrieves a list of own offer type of rooms by the ID of the associated own offer.
     *
     * @param idOwnOffer The ID of the own offer.
     * @return A list of own offer type of rooms associated with the specified own offer ID.
     */
    @Override
    public List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long idOwnOffer) {
        return ownOfferTypeOfRoomRepository.findOwnOfferTypeOfRoomByOwnOffer_IdOwnOffer(idOwnOffer);
    }

    /**
     * Deletes the provided own offer type of room.
     *
     * @param ownOfferTypeOfRoom The own offer type of room to be deleted.
     */
    @Override
    public void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        ownOfferTypeOfRoomRepository.delete(ownOfferTypeOfRoom);
    }
}
