package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.impl.AuthenticationHelper;
import biuropodrozy.gotravel.service.OwnOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * Helper class for authentication.
     */
    private final AuthenticationHelper authenticationHelper;

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
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            double price = ownOfferService.getTotalPrice(ownOffer, authenticationUser);
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
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            long idOwnOffer = ownOfferService.saveOwnOffer(ownOffer, authenticationUser);
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
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            ownOfferService.updatePaymentStatus(idOwnOffer);
            return ResponseEntity.ok().body("Payment status changed correctly.");
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves active orders for the authenticated user based on the specified period.
     *
     * @param period The period for which to retrieve active orders (e.g., "today", "thisWeek", "thisMonth")
     * @return ResponseEntity containing the active orders for the authenticated user
     */
    @GetMapping("/getReservationActiveOrders/{period}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getOwnOffersActiveOrders(@PathVariable String period) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            return ResponseEntity.ok(ownOfferService.getOwnOffersActiveOrders(authenticationUser, period));
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Deletes an own offer with the specified ID.
     *
     * @param idOwnOffer The ID of the own offer to be deleted
     * @return ResponseEntity indicating the success or failure of the deletion operation
     */
    @DeleteMapping("/deleteReservation/{idOwnOffer}")
    ResponseEntity<?> deleteOwnOffer(@PathVariable final Long idOwnOffer) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            ownOfferService.deleteOwnOffer(idOwnOffer);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves the invoice PDF for the specified own offer.
     *
     * @param idOwnOffer The ID of the own offer for which to retrieve the invoice
     * @return ResponseEntity containing the invoice PDF as a byte array
     */
    @GetMapping("/getInvoice/{idOwnOffer}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getReservationActiveOrders(@PathVariable Long idOwnOffer) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            byte[] pdfBytes = ownOfferService.getReservationInvoice(idOwnOffer);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "invoice.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
