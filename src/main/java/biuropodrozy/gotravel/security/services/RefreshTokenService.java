package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.exception.TokenRefreshException;
import biuropodrozy.gotravel.model.RefreshToken;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.RefreshTokenRepository;
import biuropodrozy.gotravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing refresh tokens.
 */
@Service
public class RefreshTokenService {

    /**
     * The expiration time for refresh tokens in milliseconds.
     */
    @Value("${gotravel.app.jwtRefreshExpirationMs}")
    private Long refreshTokenExpiration;

    /**
     * Repository for managing refresh tokens.
     */
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    /**
     * Repository for managing users.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a refresh token by its token string.
     *
     * @param token the token string
     * @return an Optional containing the refresh token, or empty if not found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Creates a new refresh token for the specified user.
     *
     * @param userId the ID of the user
     * @return the created refresh token
     */
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Verifies if a refresh token has expired.
     *
     * @param token the refresh token
     * @return the same refresh token if not expired
     * @throws TokenRefreshException if the token has expired
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    /**
     * Deletes refresh tokens associated with the specified user ID.
     *
     * @param userId the ID of the user
     */
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId)));
    }
}
