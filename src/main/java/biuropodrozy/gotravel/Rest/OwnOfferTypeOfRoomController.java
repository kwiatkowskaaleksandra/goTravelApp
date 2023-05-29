package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.OwnOffer;
import biuropodrozy.gotravel.Model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.Model.TypeOfRoom;
import biuropodrozy.gotravel.Service.OwnOfferService;
import biuropodrozy.gotravel.Service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.Service.TypeOfRoomService;
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
