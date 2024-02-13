//package biuropodrozy.gotravel.service.impl;
//
//import biuropodrozy.gotravel.model.City;
//import biuropodrozy.gotravel.model.Country;
//import biuropodrozy.gotravel.repository.CityRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class CityServiceImplTest {
//
//    @Mock
//    private CityRepository cityRepository;
//    @InjectMocks
//    private CityServiceImpl cityService;
//    private City city;
//
//    @BeforeEach
//    public void setUp(){
//        Country country = new Country();
//        country.setIdCountry(1);
//        country.setNameCountry("Polska");
//
//        city = new City();
//        city.setIdCity(1);
//        city.setNameCity("Kielce");
//        city.setCountry(country);
//    }
//
//    @Test
//    void getCitiesByCountry_IdCountry() {
//        given(cityRepository.findByCountry_IdCountry(1)).willReturn(List.of(city));
//        List<City> cityList = cityService.getCitiesByCountry_IdCountry(1);
//        assertEquals(List.of(city).size(), cityList.size());
//    }
//}