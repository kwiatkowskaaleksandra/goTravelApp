package biuropodrozy.gotravel.user;

import biuropodrozy.gotravel.exception.DuplicatedUserInfoException;
import biuropodrozy.gotravel.exception.PasswordException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.user.dto.request.PasswordRequest;
import biuropodrozy.gotravel.user.dto.request.UserTotpRequest;

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
     * @param existingUser The existing user whose data is being updated.
     * @param user         The user object containing updated data.
     * @return The updated user object.
     * @throws UserException If any required data is missing or if the zip code or phone number length is invalid.
     */
    User updateUserData(User existingUser, User user);

    /**
     * Saves a user to the database after validating the provided user data.
     *
     * @param user the user to be saved
     * @throws DuplicatedUserInfoException if a user with the same email or username already exists
     * @throws UserException               if the provided email address is incorrectly formatted
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
     * Performs password validation checks on the provided password request.
     *
     * @param passwordRequest The password request containing the new password and repeated password.
     * @throws PasswordException If the passwords provided are different, or if the new password does not meet the required criteria.
     */
    void passwordChecking(PasswordRequest passwordRequest);

    /**
     * Changes the password for the given user.
     *
     * @param user            The user for whom the password is being changed.
     * @param passwordRequest The password request containing the old and new passwords.
     * @throws PasswordException If the old password entered is different from the current one,
     *                           if the new password is the same as the previous one,
     *                           or if the new password does not meet the required criteria.
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

    /**
     * Verifies a registration link using the provided verification code.
     * This method checks if a user with the given verification code exists and is inactive.
     * If such a user is found, their verification code is cleared, and their activity status is set to true.
     *
     * @param verificationRegisterCode The verification code extracted from the registration link.
     * @return true if the registration link is successfully verified and the user's activity status is updated, false otherwise.
     */
    boolean verifyRegisterLink(String verificationRegisterCode);

    /**
     * Updates the email address of a user.
     * This method validates the provided old and new email addresses,
     * updates the email address for the user with the old email address,
     * and sends a confirmation email for the new email address if the user's provider is local.
     *
     * @param oldEmail The old email address of the user.
     * @param newEmail The new email address to be assigned to the user.
     * @throws UserException If there is no user with the provided old email address,
     *                       or if the provided new email address is incorrectly formatted.
     * @throws DuplicatedUserInfoException If a user with the provided new email address already exists.
     */
    void updateUserEmail(String oldEmail, String newEmail);

    /**
     * Resets the password for a user.
     * This method sends an email to the user with instructions for resetting their password.
     *
     * @param email The email address of the user whose password is to be reset.
     */
    void resetPassword(String email);

    /**
     * Changes the password for a user using a reset password link.
     * This method decodes the email address from the reset password link, validates and changes the password,
     * and updates the user's password in the database.
     *
     * @param passwordRequest The password request containing the new password and confirmation.
     * @param email The encoded email address obtained from the reset password link.
     */
    void changePasswordFromResetLink(PasswordRequest passwordRequest, String email);

}
