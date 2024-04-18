package biuropodrozy.gotravel.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class CountryControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CountryController countryController = new CountryController(countryService);
        mockMvc = standaloneSetup(countryController).build();
    }

    @Test
    void readAllCountries() throws Exception {
        Country country1 = new Country(1, "Polska", null);
        Country country2 = new Country(2, "Włochy", null);
        given(countryService.getAllCountries()).willReturn(Arrays.asList(country1, country2));

        mockMvc.perform(get("/api/country/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(country1, country2))));

        verify(countryService, times(1)).getAllCountries();
    }
}