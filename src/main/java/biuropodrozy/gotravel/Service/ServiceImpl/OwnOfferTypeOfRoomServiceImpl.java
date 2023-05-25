package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.Repository.OwnOfferTypeOfRoomRepository;
import biuropodrozy.gotravel.Service.OwnOfferTypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnOfferTypeOfRoomServiceImpl implements OwnOfferTypeOfRoomService {

    private final OwnOfferTypeOfRoomRepository ownOfferTypeOfRoomRepository;

    @Override
    public OwnOfferTypeOfRoom saveOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        return ownOfferTypeOfRoomRepository.save(ownOfferTypeOfRoom);
    }

    @Override
    public List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long id) {
        return ownOfferTypeOfRoomRepository.findByOwnOffer_IdOwnOffer(id);
    }

    @Override
    public void deleteOwnOfferTypeOfRoom(OwnOfferTypeOfRoom ownOfferTypeOfRoom) {
        ownOfferTypeOfRoomRepository.delete(ownOfferTypeOfRoom);
    }
}
