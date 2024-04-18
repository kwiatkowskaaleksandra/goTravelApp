package biuropodrozy.gotravel.insurance;

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

class InsuranceControllerTest {

    private MockMvc mockMvc;
    @Mock
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        InsuranceController insuranceController = new InsuranceController(insuranceService);
        mockMvc = standaloneSetup(insuranceController).build();
    }

    @Test
    void getAllInsurances() throws Exception {
        Insurance insurance1 = new Insurance(1, "Standardowe", 129.99, null, null);
        Insurance insurance2 = new Insurance(2, "Rozszerzone", 189.99, null, null);
        given(insuranceService.getAll()).willReturn(Arrays.asList(insurance1, insurance2));

        mockMvc.perform(get("/api/insurance/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(insurance1, insurance2))));

        verify(insuranceService, times(1)).getAll();
    }
}