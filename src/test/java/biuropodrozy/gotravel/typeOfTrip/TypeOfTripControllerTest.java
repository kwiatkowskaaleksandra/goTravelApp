package biuropodrozy.gotravel.typeOfTrip;

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

class TypeOfTripControllerTest {

    private MockMvc mockMvc;
    @Mock
    private TypeOfTripService typeOfTripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TypeOfTripController typeOfTripController = new TypeOfTripController(typeOfTripService);
        mockMvc = standaloneSetup(typeOfTripController).build();
    }

    @Test
    void getAllTypeOfTrips() throws Exception {
        TypeOfTrip typeOfTrip1 = new TypeOfTrip(1L, "Last Minute", null);
        TypeOfTrip typeOfTrip2 = new TypeOfTrip(2L, "All Inclusive", null);
        given(typeOfTripService.getAllTypeOfTrips()).willReturn(Arrays.asList(typeOfTrip1, typeOfTrip2));

        mockMvc.perform(get("/api/typeOfTrip/getAllTypeOfTrips")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(typeOfTrip1, typeOfTrip2))));

        verify(typeOfTripService, times(1)).getAllTypeOfTrips();
    }
}