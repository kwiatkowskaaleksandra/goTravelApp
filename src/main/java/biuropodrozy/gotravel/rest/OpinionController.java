package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.OpinionService;
import biuropodrozy.gotravel.service.impl.AuthenticationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Opinion controller.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/opinions")
public class OpinionController {

    /**
     * The OpinionService instance used for handling opinion-related operations.
     */
    private final OpinionService opinionService;

    /**
     * Helper class for authentication.
     */
    private final AuthenticationHelper authenticationHelper;

    /**
     * Retrieves the count of opinions and the total number of stars for a given trip.
     *
     * @param idTrip the ID of the trip
     * @return a ResponseEntity containing a map with the count of opinions and the total number of stars
     */
    @GetMapping("/countOpinionsAndStars")
    ResponseEntity<?> getCountOpinionsAndStars(@RequestParam Long idTrip) {
        Map<String, Object> result = opinionService.getCountOpinionsAndStars(idTrip);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Retrieves all opinions associated with a specific trip.
     *
     * @param idTrip    the ID of the trip
     * @param sortType  the sorting type for opinions
     * @param page      the page number
     * @param size      the size of the page
     * @return a ResponseEntity containing a Page of opinions associated with the trip
     */
    @GetMapping("/{idTrip}")
    ResponseEntity<Page<Opinion>> getAllOpinionByIdTrip(@PathVariable final Long idTrip, @RequestParam("sortType") String sortType,
                                                        @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(opinionService.getOpinionsByIdTrip(idTrip, sortType, pageable));
    }

    /**
     * Retrieves the total count of opinions associated with a specific trip.
     *
     * @param idTrips    the ID of the trip
     * @return a ResponseEntity containing the total count of opinions associated with the trip
     */
    @GetMapping("/countOpinions/{idTrips}")
    ResponseEntity<Integer> countOpinionsByIdTrip(@PathVariable Long idTrips) {
        return ResponseEntity.ok(opinionService.countOpinionsByIdTrip(idTrips));
    }

    /**
     * Creates a new opinion.
     * Requires the user to be authenticated with the role 'USER'.
     *
     * @param opinion the opinion to be created
     * @return a ResponseEntity indicating the success of the operation
     */
    @PostMapping("/addOpinion")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Void> createOpinion(@RequestBody final Opinion opinion) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            opinionService.saveOpinion(opinion, authenticationUser);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Deletes an opinion by its ID.
     * Requires the user to be authenticated with the role 'USER'.
     *
     * @param idOpinion the ID of the opinion to be deleted
     * @return a ResponseEntity indicating the success of the operation
     */
    @DeleteMapping("/deleteOpinion/{idOpinion}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteOpinion(@PathVariable final int idOpinion) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            opinionService.deleteOpinion(idOpinion);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
