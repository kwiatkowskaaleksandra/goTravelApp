//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.city.City;
//import biuropodrozy.gotravel.country.Country;
//import biuropodrozy.gotravel.city.CityService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class CityControllerTest {
//
//    @Mock
//    private CityService cityService;
//
//    @InjectMocks
//    private CityController cityController;
//
//    @Test
//    void filterByCountry() {
//        Country country = new Country();
//        country.setIdCountry(1);
//        country.setNameCountry("Polska");
//
//        City city1 = new City();
//        city1.setIdCity(1);
//        city1.setNameCity("Warszawa");
//        city1.setCountry(country);
//
//        City city2 = new City();
//        city2.setIdCity(2);
//        city2.setNameCity("Kielce");
//        city2.setCountry(country);
//
//        List<City> cityList = new ArrayList<>();
//        cityList.add(city1);
//        cityList.add(city2);
//
//        when(cityService.getCitiesByCountry_IdCountry(1)).thenReturn(cityList);
//        ResponseEntity<List<City>> response = cityController.filterByCountry(1);
//        HttpStatusCode status = response.getStatusCode();
//        List<City> cities = response.getBody();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//        assert cities != null;
//        assertEquals(cities.size(), cityList.size());
//    }
//}