package biuropodrozy.gotravel.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static biuropodrozy.gotravel.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

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
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/csrf", method = RequestMethod.GET)
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

}
