package biuropodrozy.gotravel.payment;

import biuropodrozy.gotravel.payment.dto.request.PaymentRequest;
import biuropodrozy.gotravel.payment.dto.request.TokenPayment;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Mock
    private PaymentRequest paymentRequest;

    @Mock
    private TokenPayment tokenPayment;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        when(paymentRequest.getToken()).thenReturn(tokenPayment);
        when(tokenPayment.getId()).thenReturn("tok_visa");
        when(paymentRequest.getAmount()).thenReturn(1000);
        when(paymentRequest.getCurrency()).thenReturn(PaymentRequest.Currency.USD);
    }

    @Test
    void testInit() {
        assertDoesNotThrow(() -> paymentService.init());
    }

    @Test
    void testChargeSuccess() throws StripeException {
        try (MockedStatic<Charge> mockedCharge = Mockito.mockStatic(Charge.class)) {
            Charge mockChargeInstance = mock(Charge.class);
            when(mockChargeInstance.getId()).thenReturn("ch_1234");

            mockedCharge.when(() -> Charge.create(any(Map.class))).thenReturn(mockChargeInstance);

            String chargeId = paymentService.charge(paymentRequest);
            assertEquals("ch_1234", chargeId);
        }
    }

    @Test
    void testChargeFailure() {
        try (MockedStatic<Charge> mockedCharge = Mockito.mockStatic(Charge.class)) {
            StripeException exception = mock(StripeException.class);
            when(exception.getMessage()).thenReturn("Error");
            mockedCharge.when(() -> Charge.create(any(Map.class))).thenThrow(exception);

            StripeException thrownException = assertThrows(StripeException.class, () -> paymentService.charge(paymentRequest));
            assertEquals("Error", thrownException.getMessage());
        }
    }

}