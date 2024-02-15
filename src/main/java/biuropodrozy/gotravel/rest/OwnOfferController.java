package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.service.OwnOfferService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Own offer controller.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
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
     * Retrieves the total price of the given own offer.
     * This endpoint requires the user to have the 'USER' role.
     *
     * @param ownOffer The own offer for which the total price needs to be calculated.
     * @return ResponseEntity containing the total price if the user is authenticated and authorized,
     *         otherwise returns UNAUTHORIZED status.
     */
    @PostMapping("/getTotalPrice")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTotalPrice(@RequestBody OwnOffer ownOffer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            double price = ownOfferService.getTotalPrice(ownOffer, existingUser);
            return ResponseEntity.ok().body(price);
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Creates a new own offer based on the provided own offer data.
     * This endpoint requires the user to be authenticated.
     *
     * @param ownOffer The own offer to be created.
     * @return A ResponseEntity containing a message indicating the success of the operation and the ID of the newly created own offer.
     */
    @PostMapping("/createOwnOffer")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> createOwnOffer(@RequestBody final OwnOffer ownOffer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            long idOwnOffer = ownOfferService.saveOwnOffer(ownOffer, existingUser);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "theTripHasBeenBooked");
            response.put("idOwnOffer", idOwnOffer);
            return ResponseEntity.ok().body(response);
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Updates the payment status of an own offer.
     * This endpoint allows users with the role 'USER' to update the payment status of their own offer.
     * The payment status is changed for the offer identified by the provided ID.
     *
     * @param idOwnOffer The ID of the own offer whose payment status is to be updated.
     * @return A ResponseEntity with an appropriate message indicating whether the payment status was successfully updated or not.
     */
    @PutMapping("/updatePaymentStatus")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> updatePaymentStatus(@RequestBody final long idOwnOffer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            userService.validateAndGetUserByUsername(userDetails.getUsername());
            ownOfferService.updatePaymentStatus(idOwnOffer);
            return ResponseEntity.ok().body("Payment status changed correctly.");
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//    /**
//     * Get all own offers by username response entity.
//     *
//     * @param username the username
//     * @return the list of own offer response entity
//     */
//    @GetMapping("/getByUsername/{username}")
//    ResponseEntity<List<OwnOffer>> getAllByUsername(@PathVariable final String username) {
//        return ResponseEntity.ok(ownOfferService.getAllOwnOfferByUsername(username));
//    }

//    /**
//     * Delete own offer response entity.
//     *
//     * @param idOwnOffer the id own offer
//     * @return the response entity
//     */
//    @DeleteMapping("/deleteOwnOffer/{idOwnOffer}")
//    ResponseEntity<?> deleteOwnOffer(@PathVariable final Long idOwnOffer) {
//        OwnOffer ownOffer = ownOfferService.getOwnOfferByIdOwnOffer(idOwnOffer);
//
//        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());
//        ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));
//
//        ownOffer.setOfferAttraction(null);
//        ownOfferService.saveOwnOffer(ownOffer);
//
//        ownOfferService.deleteOwnOffer(ownOffer);
//        return ResponseEntity.ok().build();
//    }

}
