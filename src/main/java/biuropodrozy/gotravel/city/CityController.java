package biuropodrozy.gotravel.city;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type City controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityController {

    /**
     * The CityService instance used for handling city-related operations.
     */
    private final CityService cityService;

    /**
     * Get all cities by id country response entity.
     *
     * @param idCountry the id country
     * @return list of cities response entity
     */
    @GetMapping("/all/{idCountry}")
    ResponseEntity<List<City>> filterByCountry(@PathVariable final int idCountry) {
        return ResponseEntity.ok(cityService.getCitiesByCountry_IdCountry(idCountry));
    }
}
