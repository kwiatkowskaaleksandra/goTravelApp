package biuropodrozy.gotravel.Exception;/*
 * @project gotravel
 * @author kola
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
