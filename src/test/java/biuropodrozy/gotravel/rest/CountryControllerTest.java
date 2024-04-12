package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.country.Country;
import biuropodrozy.gotravel.country.CountryController;
import biuropodrozy.gotravel.country.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @Test
    void readAllCountries() {
        Country country1 = new Country();
        country1.setIdCountry(1);
        country1.setNameCountry("Polska");

        Country country2 = new Country();
        country2.setIdCountry(2);
        country2.setNameCountry("Włochy");

        List<Country> countryList = new ArrayList<>();
        countryList.add(country1);
        countryList.add(country2);

        when(countryService.getAllCountries()).thenReturn(countryList);

        ResponseEntity<List<Country>> response = countryController.readAllCountries();
        HttpStatusCode status = response.getStatusCode();
        List<Country> countries = response.getBody();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assert countries != null;
        assertEquals(countries.size(), countryList.size());
    }
}