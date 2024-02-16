package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for handling token refresh failures.
 * Extends RuntimeException to signify unchecked exceptions that can be thrown during runtime.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

    /**
     * Constructs a new TokenRefreshException with the specified token and message.
     *
     * @param token   the token for which the refresh failed
     * @param message the detail message explaining the failure
     */
    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
