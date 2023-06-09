package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle user errors.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserException extends RuntimeException {

    /**
     * Instantiates a new user exception.
     *
     * @param message the message
     */
    public UserException(String message) {
        super(message);
    }
}
