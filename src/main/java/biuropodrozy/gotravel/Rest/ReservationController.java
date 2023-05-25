package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Exception.ReservationException;
import biuropodrozy.gotravel.Exception.UserException;
import biuropodrozy.gotravel.Model.Reservation;
import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.Model.Trip;
import biuropodrozy.gotravel.Model.User;
import biuropodrozy.gotravel.Rest.dto.UserDto;
import biuropodrozy.gotravel.Service.ReservationService;
import biuropodrozy.gotravel.Service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.Service.TripService;
import biuropodrozy.gotravel.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    private final TripService tripService;

    public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @GetMapping("/getReservation/{idReservation}")
    ResponseEntity<Reservation> getReservationByIdReservation(@PathVariable Long idReservation){
        return ResponseEntity.ok(reservationService.getReservationsByIdReservation(idReservation));
    }

    @GetMapping("/getReservationByUser/{username}")
    ResponseEntity <List<Reservation>> getReservationByUser(@PathVariable String username){
        User user = userService.validateAndGetUserByUsername(username);
        return ResponseEntity.ok(reservationService.getReservationByIdUser(user.getId()));
    }

    @PostMapping("/addReservation/{username}/{idTrip}")
    ResponseEntity<Reservation> createReservation(@PathVariable String username, @PathVariable Long idTrip, @RequestBody Reservation reservation){

        User user = userService.validateAndGetUserByUsername(username);
        Trip trip = tripService.getTripByIdTrip(idTrip);

        Date localDate = new Date();
        reservation.setUser(user);
        reservation.setDateOfReservation(localDate);
        reservation.setTrip(trip);
        reservation.setIdReservation(reservationService.getTopByOrderByIdReservation().getIdReservation()+1);

        double price = reservation.getNumberOfAdults() * trip.getPrice() + reservation.getNumberOfChildren() * (trip.getPrice()/2);
        reservation.setTotalPrice(price);

        if(reservation.getNumberOfChildren() == 0 && reservation.getNumberOfAdults() == 0 ){
            throw new ReservationException("Proszę uzupełnić inormacje o liczbie osób podróżujących.");
        }
        else if(reservation.getNumberOfChildren() != 0 && reservation.getNumberOfAdults() == 0 ){
            throw new ReservationException("Osoby poniżej 18 roku życia nie mogą podróżować bez dorosłego opiekuna.");
        }
        if(reservation.getDepartureDate().equals(LocalDate.of(1970, 1, 1))){
            throw new ReservationException("Proszę podać datę wyjazdu.");
        }
        if(user.getUsername() == null || user.getEmail() == null || user.getStreet() == null || user.getCity() == null || user.getZipCode() == null || user.getLastname() == null
                || user.getFirstname() == null|| user.getPhoneNumber() == null || user.getStreetNumber() == null){
            throw new UserException("Proszę o uzupełnienie wszytskich danych osobowych.");
        }

        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

    @DeleteMapping("/deleteReservation/{idReservation}")
    ResponseEntity<?> deleteReservation(@PathVariable Long idReservation){
        Reservation reservation = reservationService.getReservationsByIdReservation(idReservation);

        List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(idReservation);
        reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));

        reservationService.deleteReservation(reservation);
        return ResponseEntity.ok().build();
    }

}
