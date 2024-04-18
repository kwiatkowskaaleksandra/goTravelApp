package biuropodrozy.gotravel.city;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class CityControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CityController cityController = new CityController(cityService);
        mockMvc = standaloneSetup(cityController).build();
    }

    @Test
    void filterByCountry() throws Exception {
        int idCountry = 1;
        City city1 = new City(1, "Kielce", null, null, null);
        City city2 = new City(2, "Wrocław", null, null, null);
        List<City> cities = Arrays.asList(city1, city2);

        given(cityService.getCitiesByCountry_IdCountry(idCountry)).willReturn(cities);

        mockMvc.perform(get("/api/cities/all/{idCountry}", idCountry)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cities)));

        verify(cityService, times(1)).getCitiesByCountry_IdCountry(idCountry);
    }
}