package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating conflicts related to reservations.
 * This exception is typically thrown when there is a conflict or issue in the process of making or managing reservations.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ReservationException extends RuntimeException {

    /**
     * Constructs a new ReservationException with the specified detail message.
     *
     * @param message the detail message
     */
    public ReservationException(final String message) {
        super(message);
    }
}
