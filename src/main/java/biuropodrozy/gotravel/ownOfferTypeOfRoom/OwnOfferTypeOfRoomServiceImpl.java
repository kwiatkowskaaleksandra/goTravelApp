package biuropodrozy.gotravel.ownOfferTypeOfRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link OwnOfferTypeOfRoomService} interface.
 */
@RequiredArgsConstructor
@Service
public class OwnOfferTypeOfRoomServiceImpl implements OwnOfferTypeOfRoomService {

    /**
     * Repository interface for managing own offer type of rooms.
     */
    private final OwnOfferTypeOfRoomRepository ownOfferTypeOfRoomRepository;

    @Override
    public void saveOwnOfferTypeOfRoom(final OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        ownOfferTypeOfRoomRepository.save(ownOfferTypeOfRoom);
    }

    @Override
    public List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long idOwnOffer) {
        return ownOfferTypeOfRoomRepository.findOwnOfferTypeOfRoomByOwnOffer_IdOwnOffer(idOwnOffer);
    }

    @Override
    public void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        ownOfferTypeOfRoomRepository.delete(ownOfferTypeOfRoom);
    }
}
