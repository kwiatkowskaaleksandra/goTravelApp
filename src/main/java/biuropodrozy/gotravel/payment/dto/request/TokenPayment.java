package biuropodrozy.gotravel.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a payment token.
 * This class encapsulates the ID of a payment token.
 */
@Getter
@Setter
public class TokenPayment {

    /**
     * The ID of the payment token.
     */
    String id;
}
