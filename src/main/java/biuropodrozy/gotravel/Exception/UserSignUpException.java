package biuropodrozy.gotravel.Exception;/*
 * @project gotravel
 * @author kola
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserSignUpException extends RuntimeException {

    public UserSignUpException(String message) {
        super(message);
    }
}
