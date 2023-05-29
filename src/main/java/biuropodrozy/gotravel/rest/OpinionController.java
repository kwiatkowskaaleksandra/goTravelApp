package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */


import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.OpinionService;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/opinions")
public class OpinionController {

    private final OpinionService opinionService;

    private final UserService userService;

    private final TripService tripService;

    @GetMapping("/{idTrip}")
    ResponseEntity<List<Opinion>> getAllOpinionByIdTrip(@PathVariable Long idTrip) {
        return ResponseEntity.ok(opinionService.getOpinionsByIdTrip(idTrip));
    }


    @PostMapping("/addOpinion/{idUser}/{idTrip}")
    ResponseEntity<Opinion> createOpinion(@PathVariable Long idUser, @PathVariable Long idTrip, @RequestBody Opinion opinion) {

        User user = userService.getUserById(idUser);
        Trip trip = tripService.getTripByIdTrip(idTrip);
        Date localDate = new Date();
        opinion.setUser(user);
        opinion.setDate(localDate);
        opinion.setTrip(trip);

        return ResponseEntity.ok(opinionService.saveOpinion(opinion));
    }

    @DeleteMapping("/deleteOpinion/{idOpinion}")
    ResponseEntity<?> deleteOpinion(@PathVariable int idOpinion) {

        Opinion opinion = opinionService.getOpinionByIdOpinion(idOpinion);
        opinionService.deleteOpinion(opinion);

        return ResponseEntity.ok().build();
    }
}
