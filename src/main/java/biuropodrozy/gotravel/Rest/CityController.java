package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.City;
import biuropodrozy.gotravel.Model.Country;
import biuropodrozy.gotravel.Service.CityService;
import biuropodrozy.gotravel.Service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    private final CountryService countryService;

    @GetMapping("/all/{idCountry}")
    ResponseEntity<Optional<City>> filtrByCountry(@PathVariable int idCountry){
        Optional<Country> current = countryService.getCountryById(idCountry);
        return ResponseEntity.ok(cityService.getCitiesByCountry(current));
    }
}
