package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Attraction;
import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.AttractionService;
import biuropodrozy.gotravel.service.OwnOfferService;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Own offer controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ownOffer")
public class OwnOfferController {

    /**
     * Service class for managing own offers.
     */
    private final OwnOfferService ownOfferService;

    /**
     * Service class for managing own offers.
     */
    private final UserService userService;

    /**
     * Service class for managing own offers.
     */
    private final AttractionService attractionService;

    /**
     * Service class for managing own offer types of rooms.
     */
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    /**
     * Create new own offer OwnOffer.
     *
     * @param username the username
     * @param ownOffer the own offer
     * @return the response entity
     */
    @PostMapping("/addOwnOffer/{username}")
    ResponseEntity<OwnOffer> createOwnOffer(@PathVariable final String username, @RequestBody final OwnOffer ownOffer) {
        LocalDate localDate = LocalDate.now();
        User user = userService.validateAndGetUserByUsername(username);

        ownOffer.setUser(user);
        ownOffer.setDateOfReservation(localDate);
        return ResponseEntity.ok(ownOfferService.saveOwnOffer(ownOffer));
    }

    /**
     * Add attractions to own offer response entity.
     *
     * @param attractions the attractions
     * @return the response entity
     */
    @PostMapping("/addOwnOfferAttractions")
    public ResponseEntity<?> addAttractionsToOwnOffer(@RequestBody String attractions) {
        if (ownOfferService.getTopByOrderByIdOwnOffer() != null) {
            OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(ownOfferService.getTopByOrderByIdOwnOffer().getIdOwnOffer());
            Set<Attraction> attraction = new HashSet<>();
            attractions = attractions.replaceAll("\"", "");
            Optional<Attraction> atr = attractionService.getAttractionByNameAttraction(attractions);
            atr.ifPresent(attraction::add);
            ownOffer.getOfferAttraction().addAll(attraction);
            ownOfferService.saveOwnOffer(ownOffer);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Get all own offers by username response entity.
     *
     * @param username the username
     * @return the list of own offer response entity
     */
    @GetMapping("/getByUsername/{username}")
    ResponseEntity<List<OwnOffer>> getAllByUsername(@PathVariable final String username) {
        return ResponseEntity.ok(ownOfferService.getAllOwnOfferByUsername(username));
    }

    /**
     * Delete own offer response entity.
     *
     * @param idOwnOffer the id own offer
     * @return the response entity
     */
    @DeleteMapping("/deleteOwnOffer/{idOwnOffer}")
    ResponseEntity<?> deleteOwnOffer(@PathVariable final Long idOwnOffer) {
        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(idOwnOffer);

        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
        ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));

        ownOffer.setOfferAttraction(null);
        ownOfferService.saveOwnOffer(ownOffer);

        ownOfferService.deleteOwnOffer(ownOffer);
        return ResponseEntity.ok().build();
    }

}
