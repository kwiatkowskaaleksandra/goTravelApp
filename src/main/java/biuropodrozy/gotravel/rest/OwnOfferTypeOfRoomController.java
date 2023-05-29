package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.OwnOfferService;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ownOfferTypOfRooms")
public class OwnOfferTypeOfRoomController {

    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
    private final OwnOfferService ownOfferService;
    private final TypeOfRoomService typeOfRoomService;


    @PostMapping("/addOwnOfferTypeOfRooms/{nameTypeOfRoom}")
    ResponseEntity<OwnOfferTypeOfRoom> createNew(@PathVariable String nameTypeOfRoom, @RequestBody OwnOfferTypeOfRoom ownOfferTypeOfRoom) {

        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(ownOfferService.getTopByOrderByIdOwnOffer().getIdOwnOffer());
        ownOfferTypeOfRoom.setOwnOffer(ownOffer);

        TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoomByType(nameTypeOfRoom);
        ownOfferTypeOfRoom.setTypeOfRoom(typeOfRoom);

        return ResponseEntity.ok(ownOfferTypeOfRoomService.saveOwnOfferTypeOfRoom(ownOfferTypeOfRoom));

    }
}
