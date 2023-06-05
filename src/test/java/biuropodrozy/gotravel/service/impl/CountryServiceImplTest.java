package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Country;
import biuropodrozy.gotravel.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryServiceImpl countryService;
    private Country country;

    @BeforeEach
    public void setUp(){
        country = new Country();
        country.setIdCountry(1);
        country.setNameCountry("Polska");
    }

    @Test
    void getAllCountries() {
        given(countryRepository.findAll()).willReturn(List.of(country));
        List<Country> countryList = countryService.getAllCountries();
        assertEquals(List.of(country).size(), countryList.size());
    }
}