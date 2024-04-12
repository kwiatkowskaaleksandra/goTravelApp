package biuropodrozy.gotravel.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a payment request.
 * This class encapsulates information about a payment request, including the description, amount, currency,
 * email associated with the payment, and payment token.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    /**
     * Enumeration representing different currency types.
     */
    public enum Currency{
        USD, // United States Dollar
        PLN // Polish Zloty
    }

    /**
     * A brief description of the payment.
     */
    private String description;

    /**
     * The amount of the payment.
     */
    private int amount;

    /**
     * The currency of the payment.
     */
    private Currency currency;

    /**
     * The email associated with the payment.
     */
    private String stripeEmail;

    /**
     * The payment token.
     */
    private TokenPayment token;
}
