package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.repository.OwnOfferTypeOfRoomRepository;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
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
