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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private TotpService totpService;
    @Mock private MailService mailService;
    @Mock @Qualifier("newRegistrationLink") private TemplateDataStrategy templateDataStrategyNewRegisterLink;
    @Mock @Qualifier("confirmNewEmailLink") private TemplateDataStrategy templateDataStrategyConfirmNewEmailLink;
    @Mock @Qualifier("resetPasswordLink") private TemplateDataStrategy templateDataStrategyResetPasswordLink;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder, totpService, mailService, templateDataStrategyNewRegisterLink, templateDataStrategyConfirmNewEmailLink, templateDataStrategyResetPasswordLink);
    }

    @Test
    void getUserByUsername_WhenUserExists() {
        String username = "testUser";
        User user = new User();
        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getUserByUsername_WhenUserDoesNotExist() {
        String username = "nonExistingUser";
        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByUsername(username);

        assertTrue(result.isEmpty());
    }

    @Test
    void hasUserWithUsername_WhenUserExists() {
        String username = "existingUser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean result = userService.hasUserWithUsername(username);

        assertTrue(result);
    }

    @Test
    void hasUserWithUsername_WhenUserDoesNotExist() {
        String username = "nonExistingUser";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        boolean result = userService.hasUserWithUsername(username);

        assertFalse(result);
    }

    @Test
    void hasUserWithEmail_WhenUserExists() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.hasUserWithEmail(email);

        assertTrue(result);
    }

    @Test
    void hasUserWithEmail_WhenUserDoesNotExist() {
        String email = "nonExisting@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = userService.hasUserWithEmail(email);

        assertFalse(result);
    }

    @Test
    void validateAndGetUserByUsername() {
        String username = "existingUser";
        User user = new User();
        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(Optional.of(user));

        User result = userService.validateAndGetUserByUsername(username);

        assertEquals(user, result);
    }

    @Test
    void validateAndGetUserByUsername_WhenUserDoesNotExist() {
        String username = "nonExistingUser";
        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.validateAndGetUserByUsername(username));
    }

    @Test
    void updateUserData_WhenAllDataCompleted() {
        User existingUser = new User();
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");

        User result = userService.updateUserData(existingUser, user);

        assertEquals(existingUser, result);
        assertEquals(user.getFirstname(), existingUser.getFirstname());
        assertEquals(user.getLastname(), existingUser.getLastname());
        assertEquals(user.getPhoneNumber(), existingUser.getPhoneNumber());
        assertEquals(user.getCity(), existingUser.getCity());
        assertEquals(user.getStreet(), existingUser.getStreet());
        assertEquals(user.getStreetNumber(), existingUser.getStreetNumber());
        assertEquals(user.getZipCode(), existingUser.getZipCode());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserData_WhenDataIncomplete_ShouldThrowException() {
        User existingUser = new User();
        User user = new User();
        user.setUsername("Username");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");

        UserException exception = assertThrows(UserException.class, () -> userService.updateUserData(existingUser, user));

        verify(userRepository, never()).save(any(User.class));
        assertEquals("allDataShouldBeCompleted", exception.getMessage());
    }

    @Test
    void updateUserData_WhenInvalidZipCode_ShouldThrowException() {
        User existingUser = new User();
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("1234");

        UserException exception = assertThrows(UserException.class, () -> userService.updateUserData(existingUser, user));

        verify(userRepository, never()).save(any(User.class));
        assertEquals("thePostcodeMustContainFiveDigits", exception.getMessage());
    }

    @Test
    void updateUserData_WhenInvalidPhoneNumber_ShouldThrowException() {
        User existingUser = new User();
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("1234");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");

        UserException exception = assertThrows(UserException.class, () -> userService.updateUserData(existingUser, user));

        verify(userRepository, never()).save(any(User.class));
        assertEquals("thePhoneNumberMustContainNineDigits", exception.getMessage());
    }

    @Test
    void saveUser_WhenUserIsValid_ShouldSaveUserAndSendEmail() {
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");
        user.setEmail("john.doe@example.com");

        userService.saveUser(user);

        verify(userRepository, times(1)).save(user);
        verify(mailService, times(1)).sendMail(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void saveUser_WhenEmailAlreadyExists_ShouldThrowException() {
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");
        user.setEmail("john.doe@example.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> userService.saveUser(user));

        assertEquals("emailAlreadyExists", exception.getMessage());
        verify(userRepository, never()).save(user);
        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void saveUser_WhenUsernameAlreadyExists_ShouldThrowException() {
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");
        user.setEmail("john.doe@example.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> userService.saveUser(user));

        assertEquals("usernameAlreadyExists", exception.getMessage());
        verify(userRepository, never()).save(user);
        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void saveUser_WhenEmailIsIncorrect_ShouldThrowException() {
        User user = new User();
        user.setUsername("Username");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhoneNumber("123456789");
        user.setCity("Kielce");
        user.setStreet("Warszawska");
        user.setStreetNumber("12b/4");
        user.setZipCode("12345");
        user.setEmail("john.doeexample.com");

        UserException exception = assertThrows(UserException.class, () -> userService.saveUser(user));

        assertEquals("emailIsIncorrectlyFormatted", exception.getMessage());
        verify(userRepository, never()).save(user);
        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void save() {
        User user = new User();

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser() {
        User user = new User();

        userService.deleteUser(user);

        assertFalse(user.isActivity());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void passwordChecking_ShouldNotThrowException() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "Password123!", "Password123!");

        assertDoesNotThrow(() -> userService.passwordChecking(passwordRequest));
    }

    @Test
    void passwordChecking_ThrowException_PasswordsAreDifferent() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "Password234!", "Password123!");

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.passwordChecking(passwordRequest));

        assertEquals("passwordsProvidedAreDifferent", exception.getMessage());
    }

    @Test
    void passwordChecking_ThrowException_WrongLength() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "Password", "Password");

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.passwordChecking(passwordRequest));

        assertEquals("passwordMustBeComposedOfAtLeast10Characters", exception.getMessage());
    }

    @Test
    void passwordChecking_ThrowException_NoLargeCharacters() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "password123!", "password123!");

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.passwordChecking(passwordRequest));

        assertEquals("passwordMustContainAtLeastOneUppercaseLatter", exception.getMessage());
    }

    @Test
    void passwordChecking_ThrowException_NoLowCharacters() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "PASSWORD123!", "PASSWORD123!");

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.passwordChecking(passwordRequest));

        assertEquals("passwordMustContainAtLeastOneLowercaseLetter", exception.getMessage());
    }

    @Test
    void passwordChecking_ThrowException_NoNumbers() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "PasswordUser!", "PasswordUser!");

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.passwordChecking(passwordRequest));

        assertEquals("passwordMustContainAtLeastOneNumber", exception.getMessage());
    }

    @Test
    void passwordChecking_ThrowException_NoSpecialCharacters() {
        PasswordRequest passwordRequest = new PasswordRequest(null, "PasswordUser123", "PasswordUser123");

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.passwordChecking(passwordRequest));

        assertEquals("passwordMustContainAtLeastOneSpecialCharacter", exception.getMessage());
    }

    @Test
    void changePassword_ShouldChangePassword() {
        User user = new User();
        String oldPassword = "oldPassword123.";
        String newPassword = "newPassword123.";
        String newRepeatedPassword = "newPassword123.";
        PasswordRequest passwordRequest = new PasswordRequest(oldPassword, newRepeatedPassword, newPassword);

        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);

        userService.changePassword(user, passwordRequest);

        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changePassword_oldPasswordIsDifferentFromTheCurrentOne() {
        User user = new User();
        user.setPassword("myPassword99!");
        String oldPassword = "oldPassword123.";
        String newPassword = "newPassword123.";
        String newRepeatedPassword = "newPassword123.";
        PasswordRequest passwordRequest = new PasswordRequest(oldPassword, newRepeatedPassword, newPassword);

        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(false);
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.changePassword(user, passwordRequest));

        assertEquals("theOldPasswordEnteredIsDifferentFromTheCurrentOne", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void changePassword_oldPasswordIsNotDifferentFromThePreviousOne() {
        User user = new User();
        user.setPassword("oldPassword1234.");
        String oldPassword = "oldPassword1234.";
        String newPassword = "oldPassword1234.";
        String newRepeatedPassword = "oldPassword1234.";
        PasswordRequest passwordRequest = new PasswordRequest(oldPassword, newRepeatedPassword, newPassword);

        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(true);

        PasswordException exception = assertThrows(PasswordException.class, () -> userService.changePassword(user, passwordRequest));

        assertEquals("theNewPasswordMustBeDifferentFromThePreviousOne", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void changeOf2FAInclusion_ShouldGenerateTokenAndQRCode() {
        User user = new User();
        boolean twoFactorAuthenticationEnable = true;
        String generatedSecret = "generatedSecret";
        String generatedQRUrl = "generatedQRUrl";

        when(totpService.generateSecret()).thenReturn(generatedSecret);
        when(totpService.generateQRUrl(user)).thenReturn(generatedQRUrl);

        String result = userService.changeOf2FAInclusion(user, twoFactorAuthenticationEnable);

        assertTrue(user.isUsing2FA());
        assertEquals(generatedSecret, user.getSecret2FA());
        assertEquals(generatedQRUrl, result);
        verify(userRepository, times(1)).save(user);
        verify(totpService, times(1)).generateSecret();
        verify(totpService, times(1)).generateQRUrl(user);
    }

    @Test
    void changeOf2FAInclusion_When2FADisabled_ShouldDisable2FA() {
        User user = new User();
        boolean twoFactorAuthenticationEnable = false;

        String result = userService.changeOf2FAInclusion(user, twoFactorAuthenticationEnable);

        assertFalse(user.isUsing2FA());
        assertNull(user.getSecret2FA());
        assertEquals("", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void verify2faCode_WhenCodeIsValid_ShouldReturnTrue() {
        String username = "user";
        String secret2FA = "secret2FA";
        int totp = 123456;
        UserTotpRequest userTotpRequest = new UserTotpRequest(username, totp);
        User user = new User();
        user.setSecret2FA(secret2FA);

        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(java.util.Optional.of(user));
        when(totpService.verifyCode(secret2FA, totp)).thenReturn(true);

        boolean result = userService.verify2faCode(userTotpRequest);

        assertTrue(result);
    }

    @Test
    void verify2faCode_WhenCodeIsInvalid_ShouldReturnFalse() {
        String username = "user";
        String secret2FA = "secret2FA";
        int totp = 123456;
        UserTotpRequest userTotpRequest = new UserTotpRequest(username, totp);
        User user = new User();
        user.setSecret2FA(secret2FA);

        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(java.util.Optional.of(user));
        when(totpService.verifyCode(secret2FA, totp)).thenReturn(false);

        boolean result = userService.verify2faCode(userTotpRequest);

        assertFalse(result);
    }

    @Test
    void isUsing2FA_WhenUserUses2FA_ShouldReturnTrue() {
        String username = "user";
        User user = new User();
        user.setUsing2FA(true);

        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(java.util.Optional.of(user));

        boolean result = userService.isUsing2FA(username);

        assertTrue(result);
    }

    @Test
    void isUsing2FA_WhenUserDoesNotUse2FA_ShouldReturnFalse() {
        String username = "user";
        User user = new User();
        user.setUsing2FA(false);

        when(userRepository.findByUsernameAndActivity(username, true)).thenReturn(java.util.Optional.of(user));

        boolean result = userService.isUsing2FA(username);

        assertFalse(result);
    }

    @Test
    void verifyRegisterLink_WhenVerificationCodeIsValidAndUserNotActive_ShouldActivateUserAndReturnTrue() {
        String verificationRegisterCode = "verificationRegisterCode";
        User user = new User();
        user.setActivity(false);

        when(userRepository.findByVerificationRegisterCode(verificationRegisterCode)).thenReturn(user);

        boolean result = userService.verifyRegisterLink(verificationRegisterCode);

        assertTrue(result);
        assertTrue(user.isActivity());
        assertNull(user.getVerificationRegisterCode());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void verifyRegisterLink_WhenVerificationCodeIsInvalid_ShouldReturnFalse() {
        String verificationRegisterCode = "invalidVerificationRegisterCode";

        when(userRepository.findByVerificationRegisterCode(verificationRegisterCode)).thenReturn(null);

        boolean result = userService.verifyRegisterLink(verificationRegisterCode);

        assertFalse(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserEmail_WhenValidEmailsProvided_ShouldUpdateEmailAndSendConfirmationEmail() {
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";
        User user = new User();
        user.setEmail(oldEmail);
        user.setProvider(OAuth2Provider.LOCAL);

        when(userRepository.findByEmail(oldEmail)).thenReturn(user);
        when(userRepository.existsByEmail(oldEmail)).thenReturn(true);
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);

        userService.updateUserEmail(oldEmail, newEmail);

        assertEquals(newEmail, user.getEmail());
        verify(mailService, times(1)).sendMail(eq(newEmail), anyString(), anyString(), anyMap());
    }

    @Test
    void updateUserEmail_ThrowsException_NoUserWithTheProvidedEmail() {
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";

        when(userRepository.existsByEmail(oldEmail)).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> userService.updateUserEmail(oldEmail, newEmail));

        assertEquals("thereIsNoUserWithTheProvidedEmail", exception.getMessage());
        verify(mailService, never()).sendMail(eq(newEmail), anyString(), anyString(), anyMap());
    }

    @Test
    void updateUserEmail_ThrowsException_emailAlreadyExists() {
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";

        when(userRepository.existsByEmail(oldEmail)).thenReturn(true);
        when(userRepository.existsByEmail(newEmail)).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> userService.updateUserEmail(oldEmail, newEmail));

        assertEquals("emailAlreadyExists", exception.getMessage());
        verify(mailService, never()).sendMail(eq(newEmail), anyString(), anyString(), anyMap());
    }

    @Test
    void updateUserEmail_ThrowsException_emailIsIncorrectlyFormatted() {
        String oldEmail = "old@example.com";
        String newEmail = "newexample.com";

        when(userRepository.existsByEmail(oldEmail)).thenReturn(true);
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> userService.updateUserEmail(oldEmail, newEmail));

        assertEquals("emailIsIncorrectlyFormatted", exception.getMessage());
        verify(mailService, never()).sendMail(eq(newEmail), anyString(), anyString(), anyMap());
    }

    @Test
    void resetPassword_WhenUserExistsAndIsLocalAndActive_ShouldSendResetEmail() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        user.setProvider(OAuth2Provider.LOCAL);
        user.setActivity(true);

        when(userRepository.findByEmail(email)).thenReturn(user);

        userService.resetPassword(email);

        verify(mailService, times(1)).sendMail(eq(email), anyString(), anyString(), anyMap());
    }

    @Test
    void resetPassword_WhenUserNotExistsOrNotLocalOrNotActive_ShouldNotSendResetEmail() {
        String email = "user@example.com";
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(null);

        userService.resetPassword(email);

        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), anyMap());

        user.setEmail(email);
        user.setProvider(OAuth2Provider.GOOGLE); // not local
        user.setActivity(true);

        when(userRepository.findByEmail(email)).thenReturn(user);
        userService.resetPassword(email);
        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), anyMap());
        user.setProvider(OAuth2Provider.LOCAL);
        user.setActivity(false);

        when(userRepository.findByEmail(email)).thenReturn(user);
        userService.resetPassword(email);
        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void changePasswordFromResetLink_WhenValidEmailAndActiveLocalUser_ShouldChangePassword() {
        String email = "user@example.com";
        String encodedEmail = Base64.getEncoder().encodeToString("user@example.com".getBytes(StandardCharsets.UTF_8));
        String newPassword = "newPassword123.";
        String newRepeatedPassword = "newPassword123.";
        String oldPassword = "oldPassword1234.";
        User user = new User();
        user.setEmail(email);
        user.setProvider(OAuth2Provider.LOCAL);
        user.setActivity(true);
        PasswordRequest passwordRequest = new PasswordRequest(oldPassword, newRepeatedPassword, newPassword);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(userRepository.findByEmail("user@example.com")).thenReturn(user);

        userService.changePasswordFromResetLink(passwordRequest, encodedEmail);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changePasswordFromResetLink_WhenInvalidEmailOrNotActiveOrNotLocalUser_ShouldNotChangePassword() {
        String email = "user@example.com";
        String encodedEmail = Base64.getEncoder().encodeToString("user@example.com".getBytes(StandardCharsets.UTF_8));
        String newPassword = "newPassword123.";
        String newRepeatedPassword = "newPassword123.";
        String oldPassword = "newPassword1234.";
        User user = new User();
        user.setEmail(email);
        user.setProvider(OAuth2Provider.GOOGLE); // not local
        user.setActivity(true);
        PasswordRequest passwordRequest = new PasswordRequest(oldPassword, newRepeatedPassword, newPassword);

        when(userRepository.findByEmail(email)).thenReturn(null);
        userService.changePasswordFromResetLink(passwordRequest, encodedEmail);

        verify(userRepository, never()).save(any());

        user.setProvider(OAuth2Provider.LOCAL);
        user.setActivity(false);

        when(userRepository.findByEmail(email)).thenReturn(user);
        userService.changePasswordFromResetLink(passwordRequest, encodedEmail);

        verify(userRepository, never()).save(any());
    }
}