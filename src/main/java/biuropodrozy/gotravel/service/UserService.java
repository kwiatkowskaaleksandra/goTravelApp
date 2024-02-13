package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.exception.PasswordException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.request.PasswordRequest;
import biuropodrozy.gotravel.rest.dto.request.UserTotpRequest;

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
     * Updates user data based on the provided user object.
     *
     * @param user The existing user whose data is being updated.
     * @param user1         The user object containing updated data.
     * @return The updated user object.
     */
    User updateUserData(User user, User user1);

    /**
     * Save new user.
     *
     * @param user the user
     */
    void saveUser(User user);

    /**
     * Save new user.
     *
     * @param user the user
     */
    void save(User user);

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

    /**
     * Performs password validation checks on the provided password request.
     *
     * @param passwordRequest The password request containing the new password and repeated password.
     */
    void passwordChecking(PasswordRequest passwordRequest);

    /**
     * Changes the password for the given user.
     *
     * @param user            The user for whom the password is being changed.
     * @param passwordRequest The password request containing the old and new passwords.
     */
    void changePassword(User user, PasswordRequest passwordRequest);

    /**
     * Changes the inclusion of Two-Factor Authentication (2FA) for the given user.
     * Generates a token for generating the QR code if 2FA is enabled.
     *
     * @param user                        The user for whom 2FA is being changed.
     * @param twoFactorAuthenticationEnable A flag indicating whether 2FA is being enabled or disabled.
     * @return The token for generating the QR code if 2FA is enabled, otherwise an empty string.
     */
    String changeOf2FAInclusion(User user, boolean twoFactorAuthenticationEnable);

    /**
     * Verifies the provided Two-Factor Authentication (2FA) code for the given user.
     *
     * @param userTotpRequest The user and the Two-Factor Authentication code to verify.
     * @return True if the code is verified successfully, otherwise false.
     */
    boolean verify2faCode(UserTotpRequest userTotpRequest);

    /**
     * Checks if the given user is using Two-Factor Authentication (2FA).
     *
     * @param username The username of the user to check.
     * @return True if the user is using 2FA, otherwise false.
     */
    boolean isUsing2FA(String username);
}
