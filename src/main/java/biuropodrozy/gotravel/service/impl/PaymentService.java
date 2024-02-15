package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.rest.dto.request.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for processing payments using the Stripe API.
 * This service class handles the charging of payments using the Stripe payment gateway.
 */
@Service
public class PaymentService {

    /**
     * The secret key for authenticating with the Stripe API.
     */
    @Value("${gotravel.app.stripeSecretKey}")
    private String secretKey;

    /**
     * Initializes the Stripe API with the secret key.
     */
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    /**
     * Processes a payment request using the Stripe API.
     * This method creates a charge for the specified payment request using the Stripe API.
     *
     * @param request The PaymentRequest object containing payment details.
     * @return The ID of the charge if the payment was successful.
     * @throws StripeException If an error occurs during the payment processing.
     */
    public String charge(PaymentRequest request) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", request.getAmount());
        chargeParams.put("currency", PaymentRequest.Currency.USD);
        chargeParams.put("source", request.getToken().getId());

        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }
}