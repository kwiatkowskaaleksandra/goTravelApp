package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating conflicts related to trip management operations.
 * This exception is typically thrown when there is a conflict in trip management, such as conflicting settings or operations.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class TripManagementException extends RuntimeException {

    /**
     * Constructs a new TripManagementException with the specified detail message.
     *
     * @param message the detail message
     */
    public TripManagementException(final String message) {super(message);}

}
