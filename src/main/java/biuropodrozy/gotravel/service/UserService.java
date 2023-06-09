package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.User;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Get optional user by username.
     *
     * @param username the username
     * @return the user
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Check if user exists by username.
     *
     * @param username the username
     * @return true or false
     */
    boolean hasUserWithUsername(String username);

    /**
     * Check if user exists by email.
     *
     * @param email the email
     * @return true or false
     */
    boolean hasUserWithEmail(String email);

    /**
     * Validate and get user by username.
     *
     * @param username the username
     * @return the user
     */
    User validateAndGetUserByUsername(String username);

    /**
     * Save user.
     *
     * @param user the user
     * @return new user
     */
    User saveUser(User user);

    /**
     * Delete user.
     *
     * @param user the user
     */
    void deleteUser(User user);

    /**
     * Get user by id user.
     *
     * @param idUser the id user
     * @return the user
     */
    User getUserById(Long idUser);

}
