package biuropodrozy.gotravel.attraction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Attraction controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attractions")
public class AttractionController {

    /**
     * The AttractionService instance used for handling attraction-related operations.
     */
    private final AttractionService attractionService;

    /**
     * Read all attractions' by id trip response entity.
     *
     * @param idTrip the id trip
     * @return the list of attractions' response entity
     */
    @GetMapping("/{idTrip}")
    ResponseEntity<List<Attraction>> readAllTrips(@PathVariable final Long idTrip) {
        return ResponseEntity.ok(attractionService.getAllByTrips_idTrip(idTrip));
    }

    /**
     * Read all attractions' response entity.
     *
     * @return the list of attractions' response entity
     */
    @GetMapping("/all")
    ResponseEntity<List<Attraction>> readAllAttraction() {
        return ResponseEntity.ok(attractionService.getAll());
    }

}
