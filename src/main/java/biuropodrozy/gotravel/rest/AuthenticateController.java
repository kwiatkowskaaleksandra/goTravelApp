package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.exception.TokenRefreshException;
import biuropodrozy.gotravel.model.RefreshToken;
import biuropodrozy.gotravel.model.Role;
import biuropodrozy.gotravel.model.RoleEnum;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.RoleRepository;
import biuropodrozy.gotravel.rest.dto.request.*;
import biuropodrozy.gotravel.rest.dto.response.AuthResponse;
import biuropodrozy.gotravel.rest.dto.response.TokenRefreshResponse;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.security.jwt.JwtUtils;
import biuropodrozy.gotravel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private final RoleRepository roleRepository;

    /**
     * The PasswordEncoder instance used for encoding passwords.
     */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtils jwtUtils;

    /**
     * The AuthenticationManager instance used for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final RefreshTokenService refreshTokenService;


    /**
     * User login.
     *
     * @param loginRequest the login request
     * @return the auth response
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
     * New user registration.
     *
     * @param signUpRequest the signup request
     * @return the auth response
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
        user.setActivity(true);

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

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            Long userId = userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
            return ResponseEntity.ok("Log out successful!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");

    }
}
