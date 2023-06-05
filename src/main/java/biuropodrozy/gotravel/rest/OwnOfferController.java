package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Attraction;
import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.AttractionService;
import biuropodrozy.gotravel.service.OwnOfferService;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ownOffer")
public class OwnOfferController {

    public static final Logger logger = LoggerFactory.getLogger(OwnOfferController.class);
    private final OwnOfferService ownOfferService;
    private final UserService userService;
    private final AttractionService attractionService;
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    @PostMapping("/addOwnOffer/{username}")
    ResponseEntity<OwnOffer> createOwnOffer(@PathVariable String username, @RequestBody OwnOffer ownOffer) {
        LocalDate localDate = LocalDate.now();
        User user = userService.validateAndGetUserByUsername(username);

        ownOffer.setUser(user);
        ownOffer.setDateOfReservation(localDate);
        return ResponseEntity.ok(ownOfferService.saveOwnOffer(ownOffer));
    }

    @PostMapping("/addOwnOfferAttractions")
    public ResponseEntity<?> addAttractionsToOwnOffer(@RequestBody String attractions) {
        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(ownOfferService.getTopByOrderByIdOwnOffer().getIdOwnOffer());
        Set<Attraction> attraction = new HashSet<>();
        attractions = attractions.replaceAll("\"", "");
        Optional<Attraction> atr = attractionService.getAttractionByNameAttraction(attractions);
        atr.ifPresent(attraction::add);
        ownOffer.getOfferAttraction().addAll(attraction);
        ownOfferService.saveOwnOffer(ownOffer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByUsername/{username}")
    ResponseEntity<List<OwnOffer>> getAllByUsername(@PathVariable String username) {
        return ResponseEntity.ok(ownOfferService.getAllOwnOfferByUsername(username));
    }

    @DeleteMapping("/deleteOwnOffer/{idOwnOffer}")
    ResponseEntity<?> deleteOwnOffer(@PathVariable Long idOwnOffer) {
        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(idOwnOffer);

        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
        ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));

        ownOffer.setOfferAttraction(null);
        ownOfferService.saveOwnOffer(ownOffer);

        ownOfferService.deleteOwnOffer(ownOffer);
        return ResponseEntity.ok().build();
    }

}
