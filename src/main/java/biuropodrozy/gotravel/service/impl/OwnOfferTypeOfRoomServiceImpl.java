package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.repository.OwnOfferTypeOfRoomRepository;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
