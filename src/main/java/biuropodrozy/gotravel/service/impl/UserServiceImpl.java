package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.UserRepository;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The User service implementation.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * The UserRepository instance used for accessing and manipulating user data.
     */
    private final UserRepository userRepository;

    /**
     * Get optional user by username.
     *
     * @param username the username
     * @return the user
     */
    @Override
    public Optional<User> getUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Check if user exists by username.
     *
     * @param username the username
     * @return true or false
     */
    @Override
    public boolean hasUserWithUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Check if user exists by email.
     *
     * @param email the email
     * @return true or false
     */
    @Override
    public boolean hasUserWithEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Validate and get user by username.
     *
     * @param username the username
     * @return the user
     */
    @Override
    public User validateAndGetUserByUsername(final String username) {
        return getUserByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("Nie ma użytkownika o nazwie: %s", username))
                );
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    @Override
    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    /**
     * Delete user.
     *
     * @param user the user
     */
    @Override
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }

    /**
     * Get user by id user.
     *
     * @param idUser the id user
     * @return the user
     */
    @Override
    public User getUserById(final Long idUser) {
        return userRepository.findUserById(idUser);
    }

}
