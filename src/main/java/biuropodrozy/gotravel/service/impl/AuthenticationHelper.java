package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Helper class for authentication-related tasks.
 */
@Slf4j
@Component
public class AuthenticationHelper {

    /**
     * The UserService instance used for authentication validation.
     */
    private final UserService userService;

    /**
     * Constructs an AuthenticationHelper instance with the provided UserService.
     *
     * @param userService The UserService instance to use for authentication validation.
     */
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    /**
     * Validates the current authentication and retrieves the associated user.
     *
     * @return The authenticated user, or null if authentication fails.
     */
    public User validateAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            return userService.validateAndGetUserByUsername(userDetails.getUsername());
        }
        return null;
    }
}
