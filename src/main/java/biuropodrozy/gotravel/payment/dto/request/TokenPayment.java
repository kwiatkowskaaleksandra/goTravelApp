package biuropodrozy.gotravel.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a payment token.
 * This class encapsulates the ID of a payment token.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenPayment {

    /**
     * The ID of the payment token.
     */
    String id;
}
