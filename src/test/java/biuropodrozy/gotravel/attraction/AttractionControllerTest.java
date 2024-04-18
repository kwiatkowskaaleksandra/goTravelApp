package biuropodrozy.gotravel.attraction;

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

class AttractionControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AttractionService attractionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AttractionController attractionController = new AttractionController(attractionService);
        mockMvc = standaloneSetup(attractionController).build();
    }

    @Test
    void readAllAttractionByTrips() throws Exception {
        Long idTrip = 1L;
        Attraction attraction1 = new Attraction(1, "Przewodnik po mieście", 750.00, null, null);
        Attraction attraction2 = new Attraction(2, "Animator dla dzieci", 600.00, null, null);
        List<Attraction> attractions = Arrays.asList(attraction1, attraction2);

        given(attractionService.getAllByTrips_idTrip(idTrip)).willReturn(attractions);

        mockMvc.perform(get("/api/attractions/{idTrip}", idTrip)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(attractions)));

        verify(attractionService, times(1)).getAllByTrips_idTrip(idTrip);

    }

    @Test
    void readAllAttraction() throws Exception {
        Attraction attraction1 = new Attraction(1, "Przewodnik po mieście", 750.00, null, null);
        Attraction attraction2 = new Attraction(2, "Animator dla dzieci", 600.00, null, null);
        given(attractionService.getAll()).willReturn(Arrays.asList(attraction1, attraction2));

        mockMvc.perform(get("/api/attractions/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(attraction1, attraction2))));

        verify(attractionService, times(1)).getAll();
    }
}