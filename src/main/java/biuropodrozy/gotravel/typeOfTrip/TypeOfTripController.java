package biuropodrozy.gotravel.typeOfTrip;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling requests related to types of trips.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/typeOfTrip")
public class TypeOfTripController {

    /**
     * Service for managing types of trips.
     */
    public final TypeOfTripService typeOfTripService;

    /**
     * Retrieves all types of trips.
     *
     * @return ResponseEntity containing a list of all types of trips
     */
    @GetMapping("/getAllTypeOfTrips")
    ResponseEntity<List<TypeOfTrip>> getAllTypeOfTrips() {
        return ResponseEntity.ok().body(typeOfTripService.getAllTypeOfTrips());
    }

}
