package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.OpinionService;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Opinion controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/opinions")
public class OpinionController {

    private final OpinionService opinionService;

    private final UserService userService;

    private final TripService tripService;


    /**
     * Get all opinions by id trip response entity.
     *
     * @param idTrip the id trip
     * @return the list of opinions response entity
     */
    @GetMapping("/{idTrip}")
    ResponseEntity<List<Opinion>> getAllOpinionByIdTrip(@PathVariable Long idTrip) {
        return ResponseEntity.ok(opinionService.getOpinionsByIdTrip(idTrip));
    }


    /**
     * Create new opinion response entity.
     *
     * @param idUser the id user
     * @param idTrip the id trip
     * @param opinion the opinion
     * @return the response entity
     */
    @PostMapping("/addOpinion/{idUser}/{idTrip}")
    ResponseEntity<Opinion> createOpinion(@PathVariable Long idUser, @PathVariable Long idTrip, @RequestBody Opinion opinion) {

        User user = userService.getUserById(idUser);
        Trip trip = tripService.getTripByIdTrip(idTrip);
        LocalDate localDate = LocalDate.now();
        opinion.setUser(user);
        opinion.setDate(localDate);
        opinion.setTrip(trip);

        return ResponseEntity.ok(opinionService.saveOpinion(opinion));
    }

    /**
     * Delete opinion by id response entity.
     *
     * @param idOpinion the id opinion
     * @return the response entity
     */
    @DeleteMapping("/deleteOpinion/{idOpinion}")
    ResponseEntity<?> deleteOpinion(@PathVariable int idOpinion) {
        Opinion opinion = opinionService.getOpinionByIdOpinion(idOpinion);
        opinionService.deleteOpinion(opinion);

        return ResponseEntity.ok().build();
    }
}
