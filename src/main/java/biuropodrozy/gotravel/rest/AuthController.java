package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.exception.DuplicatedUserInfoException;
import biuropodrozy.gotravel.exception.UserSignUpException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.UserTotp;
import biuropodrozy.gotravel.rest.dto.AuthResponse;
import biuropodrozy.gotravel.rest.dto.LoginRequest;
import biuropodrozy.gotravel.rest.dto.SignUpRequest;
import biuropodrozy.gotravel.security.TokenProvider;
import biuropodrozy.gotravel.security.TotpService;
import biuropodrozy.gotravel.security.WebSecurityConfig;
import biuropodrozy.gotravel.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Auth controller.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gotravel")
public class AuthController {

    /**
     * The UserService instance used for handling user-related operations.
     */
    private final UserService userService;

    /**
     * The PasswordEncoder instance used for encoding passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The AuthenticationManager instance used for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The TokenProvider instance used for token generation and verification.
     */
    private final TokenProvider tokenProvider;

    /**
     * The minimum length required for a password.
     */
    private static final int PASSWORD_MIN_LENGTH = 5;

    /**
     * The TotpService instance used for Time-based One-Time Password (TOTP) operations.
     */
    @Autowired
    private TotpService totpService;

    /**
     * User login.
     *
     * @param loginRequest the login request
     * @return the auth response
     */
    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody final LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    /**
     * Checking if the user is using 2FA.
     *
     * @param loginRequest the login request
     * @return true or false
     */
   @PostMapping("/findUser")
    public boolean findUser(@Valid @RequestBody final LoginRequest loginRequest) {
        User user = userService.validateAndGetUserByUsername(loginRequest.getUsername());
        return user.isUsing2FA();
    }

    /**
     * Checking if the user has entered the correct verification code 2FA.
     *
     * @param userTotp the user totp
     * @return true or false
     */
    @PostMapping("/verify")
    public boolean verifyCode(@NotEmpty @RequestBody final UserTotp userTotp) {
        User user = userService.validateAndGetUserByUsername(userTotp.getUsername());
        return totpService.verifyCode(user.getSecret2FA(), userTotp.getTotp());
    }

    /**
     * New user registration.
     *
     * @param signUpRequest the signup request
     * @return the auth response
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody final SignUpRequest signUpRequest) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Użytkownik o nazwie: %s już istnieje.", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Użytkownik o adresie email %s już istnieje.", signUpRequest.getEmail()));
        }
        if (signUpRequest.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new UserSignUpException("Hasło powinno się składać z co najmniej 5 znaków.");
        }
        if (!signUpRequest.getEmail().matches("(.*)@(.*)")) {
            throw new UserSignUpException("Podano zły adres email.");
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    /**
     * User authentication and get token.
     *
     * @param username the username
     * @param password the password
     * @return token
     */
    public String authenticateAndGetToken(final String username, final String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    /**
     * Mapping new user from signup request to user.
     *
     * @param signUpRequest the signup request
     * @return the user
     */
    public User mapSignUpRequestToUser(final SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        user.setUsing2FA(false);
        user.setSecret2FA(null);
        System.out.println(user);
        return user;
    }
}
