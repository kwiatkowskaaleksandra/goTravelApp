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
     * Find by id user.
     *
     * @param idUser the id user
     * @return the user
     */
    User findUserById(Long idUser);
}
