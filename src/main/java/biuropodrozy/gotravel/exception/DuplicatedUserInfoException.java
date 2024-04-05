package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle duplicated user info errors.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedUserInfoException extends RuntimeException {

    /**
     * Constructs a new DuplicatedUserInfoException with the specified detail message.
     *
     * @param message the detail message
     */
    public DuplicatedUserInfoException(final String message) {
        super(message);
    }
}
