package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;
    private final UserService userService;
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    private final TripService tripService;
    private static final int HALF_PRICE = 5;
    private static final LocalDate DATE_DEFAULT = LocalDate.of(1970, 1, 1);

    @GetMapping("/getReservation/{idReservation}")
    ResponseEntity<Reservation> getReservationByIdReservation(@PathVariable Long idReservation) {
        return ResponseEntity.ok(reservationService.getReservationsByIdReservation(idReservation));
    }

    @GetMapping("/getReservationByUser/{username}")
    ResponseEntity<List<Reservation>> getReservationByUser(@PathVariable String username) {
        User user = userService.validateAndGetUserByUsername(username);
        return ResponseEntity.ok(reservationService.getReservationByIdUser(user.getId()));
    }

    @PostMapping("/addReservation/{username}/{idTrip}")
    ResponseEntity<Reservation> createReservation(@PathVariable String username, @PathVariable Long idTrip, @RequestBody Reservation reservation) {

        User user = userService.validateAndGetUserByUsername(username);
        Trip trip = tripService.getTripByIdTrip(idTrip);

        LocalDate localDate = LocalDate.now();
        reservation.setUser(user);
        reservation.setDateOfReservation(localDate);
        reservation.setTrip(trip);
        reservation.setIdReservation(reservationService.getTopByOrderByIdReservation().getIdReservation() + 1);

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

    @DeleteMapping("/deleteReservation/{idReservation}")
    ResponseEntity<?> deleteReservation(@PathVariable Long idReservation) {
        Reservation reservation = reservationService.getReservationsByIdReservation(idReservation);

        List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(idReservation);
        reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));

        reservationService.deleteReservation(reservation);
        return ResponseEntity.ok().build();
    }

}
