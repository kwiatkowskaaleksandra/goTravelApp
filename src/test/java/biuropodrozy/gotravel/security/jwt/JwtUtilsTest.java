package biuropodrozy.gotravel.security.jwt;

import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Field;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtUtilsTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    private final String username = "testuser";
    private Key key;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        jwtUtils = new JwtUtils();
        Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
        jwtSecretField.setAccessible(true);
        String jwtSecret = "verysecuresecretkeythatneedstobeatleast256bitslong";
        jwtSecretField.set(jwtUtils, jwtSecret);

        Field jwtExpirationMsField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
        jwtExpirationMsField.setAccessible(true);
        Long jwtExpirationMs = 86400000L;
        jwtExpirationMsField.set(jwtUtils, jwtExpirationMs);
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    public void generateJwtToken_ReturnsValidToken() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        assertEquals(username, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    public void getUserNameFromJwtToken_ReturnsCorrectUsername() {
        String token = jwtUtils.generateJwtToken(authentication);
        String parsedUsername = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals(username, parsedUsername);
    }

    @Test
    public void validateJwtToken_WithValidToken_ReturnsTrue() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    public void validateJwtToken_WithInvalidToken_ReturnsFalse() {
        String token = "invalid.token";
        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    public void validateJwtToken_WithExpiredToken_ReturnsFalse() throws IllegalAccessException, NoSuchFieldException {
        Field jwtExpirationMsField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
        jwtExpirationMsField.setAccessible(true);
        jwtExpirationMsField.set(jwtUtils, 1L);

        String token = jwtUtils.generateJwtToken(authentication);

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void validateJwtToken_EmptyJwt_ThrowsException() {
        String emptyToken = "";
        assertFalse(jwtUtils.validateJwtToken(emptyToken));
    }
}