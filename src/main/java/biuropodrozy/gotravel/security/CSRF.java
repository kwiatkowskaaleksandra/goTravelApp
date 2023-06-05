package biuropodrozy.gotravel.security;/*
 * @project gotravel
 * @author kola
 */

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static biuropodrozy.gotravel.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

@RestController
public class CSRF {

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

}
