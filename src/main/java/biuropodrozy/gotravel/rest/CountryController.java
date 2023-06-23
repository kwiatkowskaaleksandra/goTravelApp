package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Country;
import biuropodrozy.gotravel.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Country controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/country")
public class CountryController {

    /**
     * The CountryService instance used for handling country-related operations.
     */
    private final CountryService countryService;

    /**
     * Read all countries response entity.
     *
     * @return list of counties response entity
     */
    @GetMapping("/all")
    ResponseEntity<List<Country>> readAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

}
