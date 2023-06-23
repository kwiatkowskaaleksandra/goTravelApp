package biuropodrozy.gotravel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * This class is responsible for generating and verifying JWT tokens.
 */
@Slf4j
@Component
@NoArgsConstructor
public class TokenProvider {

    /**
     * Token type for JWT.
     */
    public static final String TOKEN_TYPE = "JWT";

    /**
     * Token issuer for JWT.
     */
    public static final String TOKEN_ISSUER = "order-api";

    /**
     * Token audience for JWT.
     */
    public static final String TOKEN_AUDIENCE = "order-app";

    /**
     * Secret key for JWT generation and validation.
     */
    @Value("${gotravel.app.jwtSecret}")
    private String jwtSecret;

    /**
     * Expiration time for JWT (in milliseconds).
     */
    @Value("${gotravel.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    /**
     * Generates a JWT based on user authentication.
     *
     * @param authentication the authentication
     * @return generated jwt token
     */
    public String generate(final Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        byte[] signingKey = jwtSecret.getBytes();

        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMs).toInstant()))
                .setIssuedAt(java.util.Date.from(ZonedDateTime.now().toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .claim("firstname", user.getFirstname())
                .claim("lastname", user.getLastname())
                .claim("preferred_username", user.getUsername())
                .claim("email", user.getEmail())
                .compact();
    }

    /**
     * Validates the JWT token.
     *
     * @param token the token
     * @return an optional Jws object containing a verified JWT
     */
    public Optional<Jws<Claims>> validateTokenAndGetJws(final String token) {
        try {
            byte[] signingKey = jwtSecret.getBytes();

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return Optional.empty();
    }
}
