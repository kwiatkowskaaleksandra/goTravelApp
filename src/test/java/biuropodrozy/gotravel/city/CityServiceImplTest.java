package biuropodrozy.gotravel.city;

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
class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;
    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityService = new CityServiceImpl(cityRepository);
    }

    @Test
    void getCitiesByCountry_IdCountry() {
        int idCountry = 1;
        City city1 = new City();
        city1.setIdCity(1);
        city1.setNameCity("Kielce");
        City city2 = new City();
        city2.setIdCity(2);
        city2.setNameCity("Wrocław");

        List<City> expectedCities = Arrays.asList(city1, city2);

        given(cityRepository.findByCountry_IdCountry(idCountry)).willReturn(expectedCities);

        List<City> resultCities = cityService.getCitiesByCountry_IdCountry(idCountry);

        assertEquals(expectedCities, resultCities);

        then(cityRepository).should(times(1)).findByCountry_IdCountry(idCountry);
    }
}