package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.OwnOfferService;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Own offer type of room controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ownOfferTypOfRooms")
public class OwnOfferTypeOfRoomController {

    /**
     * Service class for managing own offer type of rooms.
     */
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    /**
     * Service class for managing own offers.
     */
    private final OwnOfferService ownOfferService;

    /**
     * Service class for managing types of rooms.
     */
    private final TypeOfRoomService typeOfRoomService;

    /**
     * Create new offer type of room response entity.
     *
     * @param nameTypeOfRoom the name type of room
     * @param ownOfferTypeOfRoom the own offer type of room
     * @return the response entity
     */
    @PostMapping("/addOwnOfferTypeOfRooms/{nameTypeOfRoom}")
    ResponseEntity<OwnOfferTypeOfRoom> createNew(@PathVariable final String nameTypeOfRoom,
                                                 @RequestBody final OwnOfferTypeOfRoom ownOfferTypeOfRoom) {

        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(ownOfferService.getTopByOrderByIdOwnOffer().getIdOwnOffer());
        ownOfferTypeOfRoom.setOwnOffer(ownOffer);

        TypeOfRoom typeOfRoom = typeOfRoomService.getTypeOfRoomByType(nameTypeOfRoom);
        ownOfferTypeOfRoom.setTypeOfRoom(typeOfRoom);

        return ResponseEntity.ok(ownOfferTypeOfRoomService.saveOwnOfferTypeOfRoom(ownOfferTypeOfRoom));

    }
}
