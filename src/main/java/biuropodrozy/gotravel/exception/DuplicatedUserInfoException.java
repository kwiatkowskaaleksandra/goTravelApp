package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle duplicated user info errors.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedUserInfoException extends RuntimeException {

    /**
     * Instantiates a new duplicated user info exception.
     *
     * @param message the message
     */
    public DuplicatedUserInfoException(final String message) {
        super(message);
    }
}
