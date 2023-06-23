package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Reservation controller.
 */
@RequiredArgsConstructor
@RestController
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
     * The TripService instance used for handling trip-related operations.
     */
    private final TripService tripService;

    /**
     * The constant representing half price.
     */
    private static final int HALF_PRICE = 5;

    /**
     * The default date value.
     */
    private static final LocalDate DATE_DEFAULT = LocalDate.of(1970, 1, 1);

    /**
     * Get reservation by id reservation response entity.
     *
     * @param idReservation the id reservation
     * @return the response entity
     */
    @GetMapping("/getReservation/{idReservation}")
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
     * Create new reservation response entity.
     *
     * @param username the username
     * @param idTrip the id trip
     * @param reservation the reservation
     * @return the response entity
     */
    @PostMapping("/addReservation/{username}/{idTrip}")
    ResponseEntity<Reservation> createReservation(@PathVariable final String username, @PathVariable final Long idTrip,
                                                  @RequestBody final Reservation reservation) {

        User user = userService.validateAndGetUserByUsername(username);
        Trip trip = tripService.getTripByIdTrip(idTrip);

        LocalDate localDate = LocalDate.now();
        reservation.setUser(user);
        reservation.setDateOfReservation(localDate);
        reservation.setTrip(trip);
        if (reservationService.getTopByOrderByIdReservation() != null) {
            reservation.setIdReservation(reservationService.getTopByOrderByIdReservation().getIdReservation() + 1);
        } else {
            reservation.setIdReservation(1L);
        }

        double price = reservation.getNumberOfAdults() * trip.getPrice() + reservation.getNumberOfChildren() * (trip.getPrice() / HALF_PRICE);
        reservation.setTotalPrice(price);

        if (reservation.getNumberOfChildren() == 0 && reservation.getNumberOfAdults() == 0) {
            throw new ReservationException("Proszę uzupełnić inormacje o liczbie osób podróżujących.");
        } else if (reservation.getNumberOfChildren() != 0 && reservation.getNumberOfAdults() == 0) {
            throw new ReservationException("Osoby poniżej 18 roku życia nie mogą podróżować bez dorosłego opiekuna.");
        }
        if (reservation.getDepartureDate().equals(LocalDate.of(DATE_DEFAULT.getYear(), DATE_DEFAULT.getMonth(), DATE_DEFAULT.getDayOfMonth()))) {
            throw new ReservationException("Proszę podać datę wyjazdu.");
        }
        if (user.getUsername() == null || user.getEmail() == null || user.getStreet() == null || user.getCity() == null || user.getZipCode() == null || user.getLastname() == null
                || user.getFirstname() == null || user.getPhoneNumber() == null || user.getStreetNumber() == null) {
            throw new UserException("Proszę o uzupełnienie wszytskich danych osobowych.");
        }

        return ResponseEntity.ok(reservationService.saveReservation(reservation));
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
