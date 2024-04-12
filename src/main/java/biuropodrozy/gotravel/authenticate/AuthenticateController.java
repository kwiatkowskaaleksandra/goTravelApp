package biuropodrozy.gotravel.authenticate;

import biuropodrozy.gotravel.authenticate.dto.request.LoginRequest;
import biuropodrozy.gotravel.authenticate.dto.request.SignUpRequest;
import biuropodrozy.gotravel.authenticate.dto.request.TokenRefreshRequest;
import biuropodrozy.gotravel.authenticate.dto.response.AuthResponse;
import biuropodrozy.gotravel.authenticate.dto.response.TokenRefreshResponse;
import biuropodrozy.gotravel.exception.TokenRefreshException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.refreshToken.RefreshToken;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.role.RoleRepository;
import biuropodrozy.gotravel.security.jwt.JwtUtils;
import biuropodrozy.gotravel.security.oauth2.OAuth2Provider;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import biuropodrozy.gotravel.user.dto.request.PasswordRequest;
import biuropodrozy.gotravel.user.dto.request.UserTotpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Auth controller.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gotravel")
public class AuthenticateController {

    /**
     * The UserService instance used for handling user-related operations.
     */
    private final UserService userService;

    /**
     * Repository for accessing role data.
     */
    private final RoleRepository roleRepository;

    /**
     * The PasswordEncoder instance used for encoding passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Utility class for handling JWT operations.
     */
    private final JwtUtils jwtUtils;

    /**
     * The AuthenticationManager instance used for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Service for managing refresh tokens.
     */
    private final RefreshTokenService refreshTokenService;

    /**
     * Helper class for authentication.
     */
    private final AuthenticationHelper authenticationHelper;

    /**
     * Authenticates a user and generates JWT token and refresh token upon successful authentication.
     * This endpoint allows a user to authenticate using their username and password. Upon successful
     * authentication, it generates a JWT token and a refresh token for the user. The generated JWT token
     * is returned in the response body along with other user details.
     *
     * @param loginRequest The LoginRequest object containing the username and password for authentication.
     * @return A ResponseEntity containing the JWT token, refresh token, user details, and roles upon successful authentication.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.validateAndGetUserByUsername(userDetails.getUsername());

        String jwtToken = jwtUtils.generateJwtToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new AuthResponse(jwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles));
    }

    /**
     * Checks if a user is using Two-Factor Authentication (2FA).
     * This endpoint allows checking if a user has enabled Two-Factor Authentication (2FA) by providing their username.
     *
     * @param username The username of the user whose 2FA status is to be checked.
     * @return true if the user is using 2FA, false otherwise.
     */
    @GetMapping("/isUsing2FA")
    public boolean isUsing2FA(@RequestParam("username") String username) {
        return userService.isUsing2FA(username);
    }

    /**
     * Checking if the user has entered the correct verification code 2FA.
     *
     * @param userTotp the user totp
     * @return true or false
     */
    @PostMapping("/verify2FACode")
    public boolean verifyCode(@RequestBody final UserTotpRequest userTotp) {
        return userService.verify2faCode(userTotp);
    }

    /**
     * Signs up a new user.
     * This endpoint allows a new user to sign up by providing their signup details.
     * It validates the signup request, saves the user in the system, and returns an HTTP 201 Created response upon successful signup.
     *
     * @param signUpRequest The SignUpRequest object containing the signup details.
     * @return A ResponseEntity indicating successful signup with an HTTP 201 Created status code.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        PasswordRequest passwordRequest = new PasswordRequest(null, signUpRequest.getPassword(), signUpRequest.getRepeatedPassword());
        userService.passwordChecking(passwordRequest);
        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        return ResponseEntity.ok().build();
    }

    /**
     * Verifies a registration link using the provided verification code.
     * This endpoint verifies a registration link by checking if the provided verification code is valid.
     * If the verification is successful, it returns a response with status 200 OK.
     * Otherwise, it throws a UserException with an error message indicating a link error.
     *
     * @param code The verification code extracted from the registration link.
     * @return ResponseEntity with status 200 OK if the registration link is successfully verified.
     * @throws UserException If there is an error in the verification link.
     */
    @GetMapping("/verifyRegisterLink")
    public ResponseEntity<?> verifyRegister(@RequestParam("code") String code) {
        if (userService.verifyRegisterLink(code)) return ResponseEntity.ok().build();
        else throw new UserException("Link error.");
    }

    /**
     * Refreshes an access token using a refresh token.
     * This endpoint allows refreshing an access token using a refresh token.
     * It verifies the refresh token, generates a new access token, and returns it in the response.
     *
     * @param request The TokenRefreshRequest object containing the refresh token.
     * @return A ResponseEntity containing a new access token upon successful token refresh.
     * @throws TokenRefreshException If the refresh token is not found in the database.
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    List<String> roles = user.getRoles().stream()
                            .map(role -> String.valueOf(role.getName()))
                            .toList();
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken, roles));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    /**
     * Logs out a user.
     * This endpoint allows a user to log out by deleting their refresh token.
     * It deletes the refresh token associated with the authenticated user and returns a success message.
     *
     * @return A ResponseEntity indicating successful logout.
     */
    @PostMapping("/signout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> logoutUser() {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            Long userId = authenticationUser.getId();
            refreshTokenService.deleteByUserId(userId);
            return ResponseEntity.ok("Log out successful!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
    }

    /**
     * Endpoint to send a password reset link to the provided email address.
     *
     * @param email The email address of the user requesting the password reset.
     * @return ResponseEntity indicating the status of the password reset link sending.
     */
    @GetMapping("/forgotPassword")
    public ResponseEntity<?> sendingAPasswordResetLink(@RequestParam("email") String email) {
        userService.resetPassword(email);
        return ResponseEntity.ok().build();
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

        user.setUsing2FA(false);
        user.setSecret2FA(null);
        user.setActivity(false);
        user.setProvider(OAuth2Provider.LOCAL);
        user.setVerificationRegisterCode(RandomString.make(64));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin" -> {
                    Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                }
                case "mod" -> {
                    Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    roles.add(modRole);
                }
                default -> {
                    Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            }
        });
        user.setRoles(roles);
        log.info("New user:" + user);
        return user;
    }
}
