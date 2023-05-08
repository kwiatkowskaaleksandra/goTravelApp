package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Exception.ReservationException;
import biuropodrozy.gotravel.Model.Reservation;
import biuropodrozy.gotravel.Model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.Model.Trip;
import biuropodrozy.gotravel.Model.User;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    private final UserService userService;

    private final TripService tripService;

    public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @GetMapping("/getReservation/{idReservation}")
    ResponseEntity<Reservation> getReservationByIdReservation(@PathVariable Long idReservation){
        return ResponseEntity.ok(reservationService.getReservationsByIdReservation(idReservation));
    }


    @PostMapping("/addReservation/{idUser}/{idTrip}")
    ResponseEntity<Reservation> createReservation(@PathVariable Long idUser, @PathVariable Long idTrip, @RequestBody Reservation reservation){

        User user = userService.getUserById(idUser);
        Trip trip = tripService.getTripByIdTrip(idTrip);

        Date localDate = new Date();
        reservation.setUser(user);
        reservation.setDateOfReservation(localDate);
        reservation.setTrip(trip);
        reservation.setIdReservation(reservationService.getTopByOrderByIdReservation().getIdReservation()+1);

        double price = reservation.getNumberOfAdults() * trip.getPrice() + reservation.getNumberOfChildren() * (trip.getPrice()/2);
        reservation.setTotalPrice(price);
        logger.warn("dcfdsf "+ reservation.getIdReservation());

        if(reservation.getNumberOfChildren() == 0 && reservation.getNumberOfAdults() == 0 ){
            throw new ReservationException("Proszę uzupełnić inormacje o liczbie osób podróżujących.");
        }
        else if(reservation.getNumberOfChildren() != 0 && reservation.getNumberOfAdults() == 0 ){
            throw new ReservationException("Osoby poniżej 18 roku życia nie mogą podróżować bez dorosłego opiekuna.");
        }
        if(reservation.getDepartureDate().equals(LocalDate.of(1970, 1, 1))){
            throw new ReservationException("Proszę podać datę wyjazdu.");
        }

        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

}
