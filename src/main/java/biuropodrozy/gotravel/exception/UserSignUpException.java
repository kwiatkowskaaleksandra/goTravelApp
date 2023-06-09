package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle user sign up errors.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserSignUpException extends RuntimeException {

    /**
     * Instantiates a new user sign up exception.
     *
     * @param message the message
     */
    public UserSignUpException(String message) {
        super(message);
    }
}
