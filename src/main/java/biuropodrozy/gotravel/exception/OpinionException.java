package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for handling conflicts related to opinions.
 * Extends RuntimeException to signify unchecked exceptions that can be thrown during runtime.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class OpinionException extends RuntimeException {

    /**
     * Constructs a new OpinionException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public OpinionException(final String message) {
        super(message);
    }
}