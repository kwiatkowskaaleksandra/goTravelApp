package biuropodrozy.gotravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception class to represent conflicts during verification of registration links.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class VerifyRegisterLinkException extends RuntimeException {

    /**
     * Constructs a new VerifyRegisterLinkException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public VerifyRegisterLinkException(final String message) {super(message);}
}
