package biuropodrozy.gotravel.user;

import biuropodrozy.gotravel.exception.DuplicatedUserInfoException;
import biuropodrozy.gotravel.exception.PasswordException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.mail.MailService;
import biuropodrozy.gotravel.mail.TemplateDataStrategy;
import biuropodrozy.gotravel.security.oauth2.OAuth2Provider;
import biuropodrozy.gotravel.security.services.TotpService;
import biuropodrozy.gotravel.user.dto.request.PasswordRequest;
import biuropodrozy.gotravel.user.dto.request.UserTotpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * The UserRepository instance used for accessing and manipulating user data.
     */
    private final UserRepository userRepository;

    /**
     * Minimum length required for a password.
     */
    private static final int PASSWORD_MIN_LENGTH = 10;

    /**
     * Length of a zip code.
     */
    private static final int ZIP_CODE_LENGTH = 5;

    /**
     * Length of a phone number.
     */
    private static final int PHONE_NUMBER_LENGTH = 9;

    /**
     * Password encoder for encoding passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Service for Time-based One-Time Passwords (TOTP).
     */
    private final TotpService totpService;

    /**
     * Service for sending emails.
     */
    private final MailService mailService;

    /**
     * TemplateDataStrategy instance for generating data required for new registration link templates.
     */
    private final TemplateDataStrategy templateDataStrategyNewRegisterLink;

    /**
     * TemplateDataStrategy instance for generating data required for confirming new email link templates.
     */
    private final TemplateDataStrategy templateDataStrategyConfirmNewEmailLink;

    /**
     * TemplateDataStrategy instance for generating data required for reset password.
     */
    private final TemplateDataStrategy templateDataStrategyResetPasswordLink;

    /**
     * Constructs a new UserServiceImpl instance with the specified dependencies.
     *
     * @param userRepository                         The repository for accessing user data.
     * @param passwordEncoder                        The encoder for encoding passwords.
     * @param totpService                            The service for handling Time-based One-Time Password (TOTP) functionality.
     * @param mailService                            The service for sending emails.
     * @param templateDataStrategyNewRegisterLink     The TemplateDataStrategy instance for generating data required for new registration link templates.
     * @param templateDataStrategyConfirmNewEmailLink The TemplateDataStrategy instance for generating data required for confirming new email link templates.
     * @param templateDataStrategyResetPasswordLink   The TemplateDataStrategy instance for generating data required for reset password.
     */
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           TotpService totpService,
                           MailService mailService,
                           @Qualifier("newRegistrationLink") TemplateDataStrategy templateDataStrategyNewRegisterLink,
                           @Qualifier("confirmNewEmailLink") TemplateDataStrategy templateDataStrategyConfirmNewEmailLink,
                           @Qualifier("resetPasswordLink") TemplateDataStrategy templateDataStrategyResetPasswordLink) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.totpService = totpService;
        this.mailService = mailService;
        this.templateDataStrategyNewRegisterLink = templateDataStrategyNewRegisterLink;
        this.templateDataStrategyConfirmNewEmailLink = templateDataStrategyConfirmNewEmailLink;
        this.templateDataStrategyResetPasswordLink = templateDataStrategyResetPasswordLink;
    }

    @Override
    public Optional<User> getUserByUsername(final String username) {
        return userRepository.findByUsernameAndActivity(username, true);
    }

    @Override
    public boolean hasUserWithUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(final String username) {
        return getUserByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("There is no username: %s", username))
                );
    }

    @Override
    public User updateUserData(User existingUser, User user) {
        if (user.getLastname() == null || user.getFirstname() == null || user.getPhoneNumber() == null || user.getCity() == null
                || user.getStreet() == null || user.getStreetNumber() == null || user.getZipCode() == null) {
            log.error("All data should be completed.");
            throw new UserException("allDataShouldBeCompleted");
        }
        if (user.getZipCode().length() != ZIP_CODE_LENGTH) {
            log.error("The postcode must contain five digits.");
            throw new UserException("thePostcodeMustContainFiveDigits");
        }
        if (user.getPhoneNumber().length() != PHONE_NUMBER_LENGTH) {
            log.error("The phone number must contain nine digits.");
            throw new UserException("thePhoneNumberMustContainNineDigits");
        }

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setCity(user.getCity());
        existingUser.setStreet(user.getStreet());
        existingUser.setStreetNumber(user.getStreetNumber());
        existingUser.setZipCode(user.getZipCode());

        save(existingUser);
        return existingUser;
    }

    @Override
    public void saveUser(final User user) {
        if (hasUserWithEmail(user.getEmail())) {
            log.error(String.format("User with e-mail address: %s already exists.", user.getEmail()));
            throw new DuplicatedUserInfoException("emailAlreadyExists");
        }
        if (hasUserWithUsername(user.getUsername())) {
            log.error(String.format("User with username: %s already exists.", user.getUsername()));
            throw new DuplicatedUserInfoException("usernameAlreadyExists");
        }
        if (!user.getEmail().matches("(.*)@(.*)")) {
            log.error("The e-mail address provided is incorrectly formatted.");
            throw new UserException("emailIsIncorrectlyFormatted");
        }

        userRepository.save(user);
        log.info("The user has successfully registered.");

        mailService.sendMail(
                user.getEmail(),
                "New registration",
                "registerEmailTemplate.ftl",
                templateDataStrategyNewRegisterLink.prepareTemplateData(user, null)
        );
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        user.setActivity(false);
        save(user);
    }

    @Override
    public void passwordChecking(PasswordRequest passwordRequest) {
        if (!passwordRequest.getNewPassword().equals(passwordRequest.getRepeatedPassword())) {
            log.error("The passwords provided are different.");
            throw new PasswordException("passwordsProvidedAreDifferent");
        }
        if (passwordRequest.getNewPassword().length() < PASSWORD_MIN_LENGTH) {
            log.error("The password must be composed of at least 10 characters.");
            throw new PasswordException("passwordMustBeComposedOfAtLeast10Characters");
        }
        if (!passwordRequest.getNewPassword().matches("(.*)[A-Z](.*)")) {
            log.error("The password must contain at least one uppercase letter");
            throw new PasswordException("passwordMustContainAtLeastOneUppercaseLatter");
        }
        if (!passwordRequest.getNewPassword().matches("(.*)[a-z](.*)")) {
            log.error("The password must contain at least one lowercase letter.");
            throw new PasswordException("passwordMustContainAtLeastOneLowercaseLetter");
        }
        if (!passwordRequest.getNewPassword().matches("(.*)[0-9](.*)")) {
            log.error("The password must contain at least one number.");
            throw new PasswordException("passwordMustContainAtLeastOneNumber");
        }
        if (!passwordRequest.getNewPassword().matches("(.*)[!@,().%&#$^-](.*)")) {
            log.error("The password must contain at least one special character.");
            throw new PasswordException("passwordMustContainAtLeastOneSpecialCharacter");
        }
        log.info("Correct password.");
    }

    @Override
    public void changePassword(User user, PasswordRequest passwordRequest) {
        if (passwordRequest.getPassword() != null && !passwordEncoder.matches(passwordRequest.getPassword(), user.getPassword())) {
            log.error("The old password entered is different from the current one.");
            throw new PasswordException("theOldPasswordEnteredIsDifferentFromTheCurrentOne");
        }
        if (passwordEncoder.matches(passwordRequest.getNewPassword(), user.getPassword())) {
            log.error("The new password must be different from the previous one.");
            throw new PasswordException("theNewPasswordMustBeDifferentFromThePreviousOne");
        }
        passwordChecking(passwordRequest);
        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        save(user);
        log.info("Password has been changed.");
    }

    @Override
    public String changeOf2FAInclusion(User user, boolean twoFactorAuthenticationEnable) {
        String token2FA = "";
        if (twoFactorAuthenticationEnable) {
            user.setUsing2FA(true);
            user.setSecret2FA(totpService.generateSecret());
            token2FA = totpService.generateQRUrl(user);
            log.info("The token to generate the QR code has been generated.");
        } else {
            user.setSecret2FA(null);
            user.setUsing2FA(false);
            log.info("2FA has been disabled.");
        }

        save(user);
        return token2FA;
    }

    @Override
    public boolean verify2faCode(UserTotpRequest userTotpRequest) {
        User user = validateAndGetUserByUsername(userTotpRequest.getUsername());
        return totpService.verifyCode(user.getSecret2FA(), userTotpRequest.getTotp());
    }

    @Override
    public boolean isUsing2FA(String username) {
        User user = validateAndGetUserByUsername(username);
        return user.isUsing2FA();
    }

    @Override
    public boolean verifyRegisterLink(String verificationRegisterCode) {
        User user = userRepository.findByVerificationRegisterCode(verificationRegisterCode);

        if (user == null || user.isActivity()) return false;
        else {
            user.setVerificationRegisterCode(null);
            user.setActivity(true);
            userRepository.save(user);
        }
        return true;
    }

    /**
     * Validates email addresses.
     * This method validates the provided old and new email addresses.
     * It checks if there is a user with the provided old email address,
     * if a user with the provided new email address already exists,
     * and if the format of the new email address is correct.
     *
     * @param oldEmail The old email address to be validated.
     * @param newEmail The new email address to be validated.
     * @throws UserException If there is no user with the provided old email address
     *                       or if the provided new email address is incorrectly formatted.
     * @throws DuplicatedUserInfoException If a user with the provided new email address already exists.
     */
    private void validatorEmail(String oldEmail, String newEmail) {
        if (!hasUserWithEmail(oldEmail)) {
            log.error("There is no user with the provided email address.");
            throw new UserException("thereIsNoUserWithTheProvidedEmail");
        }
        if (hasUserWithEmail(newEmail)) {
            log.error(String.format("User with e-mail address: %s already exists.", newEmail));
            throw new DuplicatedUserInfoException("emailAlreadyExists");
        }
        if (!newEmail.matches("(.*)@(.*)")) {
            log.error("The e-mail address provided is incorrectly formatted.");
            throw new UserException("emailIsIncorrectlyFormatted");
        }
    }

    @Override
    public void updateUserEmail(String oldEmail, String newEmail) {
        validatorEmail(oldEmail, newEmail);
        User user = userRepository.findByEmail(oldEmail);
        if (user != null && user.getProvider().equals(OAuth2Provider.LOCAL)) {
            user.setEmail(newEmail);
            mailService.sendMail(
                    newEmail,
                    "Confirm new email address",
                    "confirmNewEmailTemplate.ftl",
                    templateDataStrategyConfirmNewEmailLink.prepareTemplateData(user, null)
            );
        }
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getProvider().equals(OAuth2Provider.LOCAL) && user.isActivity()) {
            mailService.sendMail(
                    user.getEmail(),
                    "Reset password",
                    "resetPasswordEmailTemplate.ftl",
                    templateDataStrategyResetPasswordLink.prepareTemplateData(user, null)
            );
        }
    }

    @Override
    public void changePasswordFromResetLink(PasswordRequest passwordRequest, String email) {
        byte[] decodeData = Base64.getDecoder().decode(email);
        String emailDecode = new String(decodeData, StandardCharsets.UTF_8);
        User user =  userRepository.findByEmail(emailDecode);

        if (user != null && user.getProvider().equals(OAuth2Provider.LOCAL) && user.isActivity()) {
            passwordChecking(passwordRequest);
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            save(user);
            log.info("Correct rest password.");
        }
    }
}
