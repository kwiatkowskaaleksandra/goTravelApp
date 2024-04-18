package biuropodrozy.gotravel.payment;

import biuropodrozy.gotravel.payment.dto.request.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void testCompletePaymentSuccess() throws Exception {
        when(paymentService.charge(any(PaymentRequest.class))).thenReturn("ch_1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"desc\",\"amount\":100,\"currency\":\"USD\",\"stripeEmail\":\"email@example.com\",\"token\":{\"id\":\"token123\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("ch_1234"));
    }

    @Test
    public void testCompletePaymentFailure() throws Exception {

        when(paymentService.charge(any(PaymentRequest.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Failed Payment\",\"amount\":1000,\"currency\":\"USD\",\"stripeEmail\":\"fail@example.com\",\"token\":{\"id\":\"token_123\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Please check the credit card details entered"));
    }
}