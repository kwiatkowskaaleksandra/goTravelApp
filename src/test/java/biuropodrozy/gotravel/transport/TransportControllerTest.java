package biuropodrozy.gotravel.transport;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class TransportControllerTest {

    private MockMvc mockMvc;
    @Mock
    private TransportService transportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TransportController transportController = new TransportController(transportService);
        mockMvc = standaloneSetup(transportController).build();
    }

    @Test
    void readAllTransports() throws Exception {
        Transport transport1 = new Transport(1, "samolot", 500.00, null);
        Transport transport2 = new Transport(2, "bus", 100.00, null);
        given(transportService.getAllTransports()).willReturn(Arrays.asList(transport1, transport2));

        mockMvc.perform(get("/api/transport/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(transport1, transport2))));

        verify(transportService, times(1)).getAllTransports();
    }
}