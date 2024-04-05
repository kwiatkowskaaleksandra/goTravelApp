package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.RefreshToken;
import biuropodrozy.gotravel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing refresh tokens.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Finds a refresh token by its token value.
     *
     * @param token the token value to search for
     * @return an Optional containing the refresh token, if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Deletes refresh tokens associated with a specific user.
     *
     * @param user the user whose refresh tokens will be deleted
     */
    @Modifying
    void deleteByUser(User user);
}
