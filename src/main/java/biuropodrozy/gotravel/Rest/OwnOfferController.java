package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import biuropodrozy.gotravel.Model.OwnOffer;
import biuropodrozy.gotravel.Model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.Model.User;
import biuropodrozy.gotravel.Service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ownOffer")
public class OwnOfferController {

    private final OwnOfferService ownOfferService;
    private final UserService userService;
    private final AttractionService attractionService;
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    public static final Logger logger = LoggerFactory.getLogger(OwnOfferController.class);

    @PostMapping("/addOwnOffer/{username}")
    ResponseEntity<OwnOffer> createOwnOffer(@PathVariable String username, @RequestBody OwnOffer ownOffer){
        Date localDate = new Date();
        User user = userService.validateAndGetUserByUsername(username);

        ownOffer.setUser(user);
        ownOffer.setDateOfReservation(localDate);
        return ResponseEntity.ok(ownOfferService.saveOwnOffer(ownOffer));
    }

    @PostMapping("/addOwnOfferAttractions")
    public ResponseEntity<?> addAttractionsToOwnOffer( @RequestBody String attractions){
        OwnOffer ownOffer= ownOfferService.getOwnOfferByIdOwnOffer(ownOfferService.getTopByOrderByIdOwnOffer().getIdOwnOffer());
        Set<Attraction> attraction = new HashSet<>();
        attractions = attractions.replaceAll("\"","");
        Optional<Attraction> atr = attractionService.getAttractionByNameAttraction(attractions);
        atr.ifPresent(attraction::add);
        ownOffer.getOfferAttraction().addAll(attraction);
        ownOfferService.saveOwnOffer(ownOffer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByUsername/{username}")
    ResponseEntity<List<OwnOffer>> getAllByUsername(@PathVariable String username){
        return ResponseEntity.ok(ownOfferService.getAllOwnOfferByUsername(username));
    }

    @DeleteMapping("/deleteOwnOffer/{idOwnOffer}")
    ResponseEntity<?> deleteOwnOffer(@PathVariable Long idOwnOffer){
        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(idOwnOffer);

        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
        ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));

        ownOffer.setOfferAttraction(null);
        ownOfferService.saveOwnOffer(ownOffer);

        ownOfferService.deleteOwnOffer(ownOffer);
        return ResponseEntity.ok().build();
    }

}
