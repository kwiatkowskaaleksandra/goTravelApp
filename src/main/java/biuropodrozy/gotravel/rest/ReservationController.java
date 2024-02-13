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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

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
     * @return ResponseEntity indicating successful addition of the reservation if the user is authenticated and authorized,
     *         otherwise returns UNAUTHORIZED status.
     */
    @PostMapping("/addReservation")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createReservation(@RequestBody final Reservation reservation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            reservationService.saveReservation(reservation, existingUser);
            return ResponseEntity.ok().body("theTripHasBeenBooked");
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
