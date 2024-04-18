package biuropodrozy.gotravel.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CSRFTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCsrfTokenEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/csrf"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.headerName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parameterName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }
}