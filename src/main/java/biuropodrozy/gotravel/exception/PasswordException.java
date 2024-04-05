package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for handling conflicts related to passwords.
 * Extends RuntimeException to signify unchecked exceptions that can be thrown during runtime.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class PasswordException extends RuntimeException {

    /**
     * Constructs a new PasswordException with the specified detail message.
     *
     * @param message the detail message
     */
    public PasswordException(final String message) {
        super(message);
    }
}