package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating conflicts related to user trip preferences.
 * This exception is typically thrown when there is a conflict in user trip preferences, such as conflicting settings.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserTripPreferencesException extends RuntimeException {

    /**
     * Constructs a new UserTripPreferencesException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserTripPreferencesException(final String message) {
        super(message);
    }
}
