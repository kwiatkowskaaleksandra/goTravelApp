package biuropodrozy.gotravel.security.oauth2;

import biuropodrozy.gotravel.model.RefreshToken;
import biuropodrozy.gotravel.security.jwt.JwtUtils;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * The CustomAuthenticationSuccessHandler class provides custom handling for successful user authentication, including generating and sending an authentication token.
 */
@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * The provider responsible for generating authentication tokens.
     */
    private final JwtUtils jwtUtils;

    /**
     * Service for managing refresh tokens.
     */
    private final RefreshTokenService refreshTokenService;

    /**
     * The redirect URI to be used after successful authentication.
     */
    @Value("${app.oauth2.redirectUri}")
    private String redirectUri;

    /**
     * Handles successful authentication by generating an authentication token and redirecting the user to the specified target URL.
     *
     * @param request The HttpServletRequest containing the request details.
     * @param response The HttpServletResponse for sending the response.
     * @param authentication The Authentication object representing the authenticated user.
     * @throws IOException If an I/O error occurs during the redirection.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        super.clearAuthenticationAttributes(request);
    }

    /**
     * Generates an authentication token and redirects the user to the target URL.
     *
     * @param request The HttpServletRequest containing the request details.
     * @param response The HttpServletResponse for sending the response.
     * @param authentication The Authentication object representing the authenticated user.
     * @throws IOException If an I/O error occurs during the redirection.
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String targetUrl = redirectUri.isEmpty() ?
                determineTargetUrl(request, response, authentication) : redirectUri;

        String token = jwtUtils.generateJwtToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).queryParam("refresh", refreshToken.getToken()).build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
