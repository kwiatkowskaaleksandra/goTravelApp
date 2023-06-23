package biuropodrozy.gotravel.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is responsible for filtering requests and checking if the token is valid.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Header key for token in the request.
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Token prefix used in the Authorization header.
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Service for retrieving user details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Provider for generating and validating tokens.
     */
    private final TokenProvider tokenProvider;

    /**
     * This method is responsible for filtering requests and checking if the token is valid.
     *
     * @param request the request
     * @param response the response
     * @param filterChain the filterChain
     * @throws ServletException the ServletException
     * @throws IOException the IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            getJwtFromRequest(request)
                    .flatMap(tokenProvider::validateTokenAndGetJws)
                    .ifPresent(jws -> {
                        String username = jws.getBody().getSubject();
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
        } catch (Exception e) {
            log.error("Cannot set user authentication", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * This method retrieves the jwt from http servlet request.
     *
     * @param request the request
     * @return token
     */
    private Optional<String> getJwtFromRequest(final HttpServletRequest request) {
        String tokenHeader = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith(TOKEN_PREFIX)) {
            return Optional.of(tokenHeader.replace(TOKEN_PREFIX, ""));
        }
        return Optional.empty();
    }
}
