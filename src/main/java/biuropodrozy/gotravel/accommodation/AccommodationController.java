package biuropodrozy.gotravel.accommodation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Accommodation controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    /**
     * The AccommodationService instance used for handling accommodation-related operations.
     */
    private final AccommodationService accommodationService;

    /**
     * Read all accommodations response entity.
     *
     * @return the list of accommodations response entity
     */
    @GetMapping("/all")
    ResponseEntity<List<Accommodation>> readAllAccommodations() {
        return ResponseEntity.ok(accommodationService.getAllAccommodations());
    }
}
