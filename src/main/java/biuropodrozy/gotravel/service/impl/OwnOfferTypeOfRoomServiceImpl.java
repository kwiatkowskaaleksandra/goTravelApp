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

    private final OwnOfferTypeOfRoomRepository ownOfferTypeOfRoomRepository;

    /**
     * Save new own offer type of rooms
     *
     * @param ownOfferTypeOfRoom the own offer type of rooms
     * @return the own offer type of rooms
     */
    @Override
    public OwnOfferTypeOfRoom saveOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        return ownOfferTypeOfRoomRepository.save(ownOfferTypeOfRoom);
    }

    /**
     * Find own offer type of rooms by id own offer.
     *
     * @param id the id own offer
     * @return list of own offer type of rooms
     */
    @Override
    public List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long id) {
        return ownOfferTypeOfRoomRepository.findByOwnOffer_IdOwnOffer(id);
    }

    /**
     * Delete own offer type of rooms
     *
     * @param ownOfferTypeOfRoom the own offer type of rooms
     */
    @Override
    public void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        ownOfferTypeOfRoomRepository.delete(ownOfferTypeOfRoom);
    }
}
