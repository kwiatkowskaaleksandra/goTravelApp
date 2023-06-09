package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle reservations' errors.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ReservationException extends RuntimeException {

    /**
     * Instantiates a new reservations' exception.
     *
     * @param message the message
     */
    public ReservationException(String message) {
        super(message);
    }

}
