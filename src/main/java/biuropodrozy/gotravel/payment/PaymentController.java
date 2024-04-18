package biuropodrozy.gotravel.payment;

import biuropodrozy.gotravel.payment.dto.request.PaymentRequest;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling payment-related endpoints.
 * This controller provides endpoints for completing payments and handles exceptions related to Stripe operations.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    /**
     * Service for processing payments.
     */
    private final PaymentService paymentService;

    /**
     * Endpoint for completing a payment.
     * This endpoint processes a payment request and completes the payment using the provided payment details.
     *
     * @param request The PaymentRequest object containing payment details.
     * @return A ResponseEntity containing either the charge ID if the payment was successful, or an error message if the payment failed.
     * @throws StripeException If an error occurs during the Stripe payment process.
     */
    @PostMapping
    public ResponseEntity<?> completePayment(@RequestBody @Valid PaymentRequest request) throws StripeException {
        String chargeId= paymentService.charge(request);
        return chargeId!=null ? new ResponseEntity<>(chargeId, HttpStatus.OK):
                new ResponseEntity<>("Please check the credit card details entered", HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for handling Stripe-related exceptions.
     * This method handles any exceptions that occur during Stripe payment processing and returns the corresponding error message.
     *
     * @param ex The StripeException object representing the exception that occurred.
     * @return A string containing the error message associated with the exception.
     */
    @ExceptionHandler
    public String handleError(StripeException ex) {
        return ex.getMessage();
    }
}
