package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating a conflict related to user operations.
 * This exception is typically thrown when there is a conflict or inconsistency in the state or operation involving users.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserException extends RuntimeException {

    /**
     * Instantiates a new user exception.
     *
     * @param message the detail message
     */
    public UserException(final String message) {
        super(message);
    }
}
