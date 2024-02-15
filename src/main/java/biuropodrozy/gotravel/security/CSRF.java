package biuropodrozy.gotravel.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  This class is responsible for csrf token.
 */
@RestController
public class CSRF {

    /**
     *  This method is responsible for csrf token.
     *
     * @param token the csrf token
     * @return csrf token
     */
    @RequestMapping(value = "/csrf", method = RequestMethod.GET)
    public CsrfToken csrf(final CsrfToken token) {
        return token;
    }

}
