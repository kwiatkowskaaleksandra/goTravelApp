package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.OpinionService;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Opinion controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/opinions")
public class OpinionController {

    /**
     * The OpinionService instance used for handling opinion-related operations.
     */
    private final OpinionService opinionService;

    /**
     * The UserService instance used for handling user-related operations.
     */
    private final UserService userService;

    /**
     * The TripService instance used for handling trip-related operations.
     */
    private final TripService tripService;


    /**
     * Get all opinions by id trip response entity.
     *
     * @param idTrip the id trip
     * @return the list of opinions response entity
     */
    @GetMapping("/{idTrip}")
    ResponseEntity<List<Opinion>> getAllOpinionByIdTrip(@PathVariable final Long idTrip) {
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
    ResponseEntity<Opinion> createOpinion(@PathVariable final Long idUser, @PathVariable final Long idTrip,
                                          @RequestBody final Opinion opinion) {

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
    ResponseEntity<?> deleteOpinion(@PathVariable final int idOpinion) {
        Opinion opinion = opinionService.getOpinionByIdOpinion(idOpinion);
        opinionService.deleteOpinion(opinion);

        return ResponseEntity.ok().build();
    }
}
