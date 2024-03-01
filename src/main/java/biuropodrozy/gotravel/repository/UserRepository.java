package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Check if user exists by email.
     *
     * @param email the email
     * @return true or false
     */
    Boolean existsByEmail(String email);

    /**
     * Check if user exists by username.
     *
     * @param username the username
     * @return true or false
     */
    Boolean existsByUsername(String username);

    /**
     * Find optional by username user.
     *
     * @param username the username
     * @return the user
     */
    Optional<User> findByUsernameAndActivity(String username, boolean activity);

    /**
     * Retrieves a user by their verification register code.
     * This method retrieves a user from the repository using the provided verification register code.
     *
     * @param code The verification register code of the user to retrieve.
     * @return The user associated with the provided verification register code, or null if not found.
     */
    User findByVerificationRegisterCode(String code);

    /**
     * Retrieves a user by their email address.
     * This method retrieves a user from the repository using the provided email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user associated with the provided email address, or null if not found.
     */
    User findByEmail(String email);
}
