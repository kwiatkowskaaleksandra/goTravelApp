package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.impl.AuthenticationHelper;
import biuropodrozy.gotravel.service.ReservationService;
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
 * The type Reservation controller.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/reservations")
public class ReservationController {

    /**
     * The ReservationService instance used for handling reservation-related operations.
     */
    private final ReservationService reservationService;

    private final AuthenticationHelper authenticationHelper;

    /**
     * Adds a new reservation based on the provided reservation data.
     * This endpoint requires the user to have the 'USER' role.
     *
     * @param reservation The reservation to be added.
     * @return A ResponseEntity containing a message indicating the success of the operation and the ID of the newly created reservation.
     */
    @PostMapping("/addReservation")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createReservation(@RequestBody final Reservation reservation) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            long idReservation = reservationService.saveReservation(reservation, authenticationUser);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "theTripHasBeenBooked");
            response.put("idReservation", idReservation);

            return ResponseEntity.ok().body(response);
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Endpoint for updating the payment status of a reservation.
     * This endpoint allows users to update the payment status of their reservation.
     * Only users with the 'USER' role are authorized to access this endpoint.
     *
     * @param idReservation The ID of the reservation whose payment status is to be updated.
     * @return A ResponseEntity with an appropriate message indicating whether the payment status was successfully updated or not.
     */
    @PutMapping("/updatePaymentStatus")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> updatePaymentStatus(@RequestBody final long idReservation) {
        User authentication = authenticationHelper.validateAuthentication();
        if (authentication != null) {
            reservationService.updatePaymentStatus(idReservation);
            return ResponseEntity.ok().body("Payment status changed correctly.");
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Deletes the reservation with the specified ID.
     *
     * @param idReservation The ID of the reservation to be deleted
     * @return ResponseEntity indicating the success of the deletion operation
     */
    @DeleteMapping("/deleteReservation/{idReservation}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteReservation(@PathVariable final Long idReservation) {
        User authentication = authenticationHelper.validateAuthentication();
        if (authentication != null) {
            reservationService.deleteReservation(idReservation);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves active reservation orders for the authenticated user within the specified period.
     *
     * @param period The period for which active reservation orders are to be retrieved
     * @return ResponseEntity containing active reservation orders for the authenticated user
     */
    @GetMapping("/getReservationActiveOrders/{period}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getReservationActiveOrders(@PathVariable String period) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            return ResponseEntity.ok(reservationService.getReservationActiveOrders(authenticationUser, period));
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves the invoice for the specified reservation and returns it as a PDF file.
     *
     * @param idReservation The ID of the reservation for which the invoice is requested
     * @return ResponseEntity containing the invoice PDF as a byte array
     */
    @GetMapping("/getInvoice/{idReservation}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getReservationActiveOrders(@PathVariable Long idReservation) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            byte[] pdfBytes = reservationService.getReservationInvoice(idReservation);
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
