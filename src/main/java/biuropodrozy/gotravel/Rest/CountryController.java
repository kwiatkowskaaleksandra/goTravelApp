package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Country;
import biuropodrozy.gotravel.Service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/all")
    ResponseEntity<List<Country>> readAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

}
