package biuropodrozy.gotravel.accommodation;

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

class AccommodationControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AccommodationService accommodationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AccommodationController accommodationController = new AccommodationController(accommodationService);
        mockMvc = standaloneSetup(accommodationController).build();
    }

    @Test
    void readAllAccommodations() throws Exception {
        Accommodation accommodation1 = new Accommodation(1, "Hotel czterogwiazdkowy", 200.0, null, null);
        Accommodation accommodation2 = new Accommodation(2, "Hotel dwugwiazdkowy", 150.0, null, null);
        given(accommodationService.getAllAccommodations()).willReturn(Arrays.asList(accommodation1, accommodation2));

       mockMvc.perform(get("/api/accommodations/all")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(accommodation1, accommodation2))));

       verify(accommodationService, times(1)).getAllAccommodations();
    }
}