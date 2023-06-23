package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle user not found errors.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Instantiates a new user not found exception.
     *
     * @param message the message
     */
    public UserNotFoundException(final String message) {
        super(message);
    }
}
