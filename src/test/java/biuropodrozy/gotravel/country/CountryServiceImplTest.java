package biuropodrozy.gotravel.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryService = new CountryServiceImpl(countryRepository);
    }

    @Test
    void getAllCountries() {
        Country country1 = new Country();
        country1.setIdCountry(1);
        country1.setNameCountry("Polska");
        Country country2 = new Country();
        country2.setIdCountry(2);
        country2.setNameCountry("Włochy");

        given(countryRepository.findAll()).willReturn(Arrays.asList(country1, country2));

        List<Country> countries = countryService.getAllCountries();

        then(countryRepository).should(times(1)).findAll();
        assertAll("countries",
                () -> assertNotNull(countries),
                () -> assertEquals(2, countries.size()),
                () -> assertEquals("Polska", countries.get(0).getNameCountry()),
                () -> assertEquals("Włochy", countries.get(1).getNameCountry()));
    }
}