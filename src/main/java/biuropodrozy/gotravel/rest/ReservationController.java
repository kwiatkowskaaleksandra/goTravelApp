package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
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
import java.util.List;
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

    /**
     * The UserService instance used for handling user-related operations.
     */
    private final UserService userService;

    /**
     * The ReservationsTypeOfRoomService instance used for handling reservations of room types.
     */
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;

    /**
     * Get reservation by id reservation response entity.
     *
     * @param idReservation the id reservation
     * @return the response entity
     */
    @GetMapping("/getReservation/{idReservation}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Reservation> getReservationByIdReservation(@PathVariable final Long idReservation) {
        return ResponseEntity.ok(reservationService.getReservationsByIdReservation(idReservation));
    }

    /**
     * Get reservation by user response entity.
     *
     * @param username the username
     * @return the list of reservations response entity
     */
    @GetMapping("/getReservationByUser/{username}")
    ResponseEntity<List<Reservation>> getReservationByUser(@PathVariable final String username) {
        User user = userService.validateAndGetUserByUsername(username);
        return ResponseEntity.ok(reservationService.getReservationByIdUser(user.getId()));
    }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            long idReservation = reservationService.saveReservation(reservation, existingUser);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            userService.validateAndGetUserByUsername(userDetails.getUsername());
            reservationService.updatePaymentStatus(idReservation);
            return ResponseEntity.ok().body("Payment status changed correctly.");
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Delete reservation response entity.
     *
     * @param idReservation the id reservation
     * @return the response entity
     */
    @DeleteMapping("/deleteReservation/{idReservation}")
    ResponseEntity<?> deleteReservation(@PathVariable final Long idReservation) {
        Reservation reservation = reservationService.getReservationsByIdReservation(idReservation);

        List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(idReservation);
        reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));

        reservationService.deleteReservation(reservation);
        return ResponseEntity.ok().build();
    }

}
