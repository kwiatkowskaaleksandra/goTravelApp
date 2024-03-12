package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.request.PasswordRequest;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    /**
     * Service for managing user-related operations.
     */
    private final UserService userService;

    /**
     * Service for managing refresh tokens.
     */
    private final RefreshTokenService refreshTokenService;

    /**
     * Get current user response entity.
     *
     * @param currentUser the current user
     * @return the user response entity
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@AuthenticationPrincipal final UserDetailsImpl currentUser) {
        return userService.validateAndGetUserByUsername(currentUser.getUsername());
    }

    /**
     * Deactivates the user account.
     * This endpoint deactivates the user account associated with the authenticated user.
     * It deletes the user from the system and revokes any associated refresh tokens.
     *
     * @return ResponseEntity with status 200 OK and a success message if the account is deactivated successfully.
     */
    @PutMapping("/deleteUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            userService.deleteUser(existingUser);
            refreshTokenService.deleteByUserId(existingUser.getId());
            return ResponseEntity.ok().body("The account has been deactivated.");
        }

        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Updates the user's data.
     * This endpoint updates the user's data with the provided information.
     * It retrieves the authenticated user, validates the input data, and updates the user's data accordingly.
     *
     * @param user The updated user information.
     * @return ResponseEntity with status 200 OK and the updated user object if the update is successful.
     */
    @PutMapping("/updateUserData")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> updateUser(@Valid @RequestBody final User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            existingUser = userService.updateUserData(existingUser, user);
            log.info("Correct user information update.");
            return ResponseEntity.ok(existingUser);
        }

        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Updates the user's password.
     * This endpoint updates the user's password with the provided new password.
     * It retrieves the authenticated user, validates the input password request, and updates the password accordingly.
     *
     * @param passwordRequest The password request object containing the original password, repeated password, and new password.
     * @return ResponseEntity with status 200 OK and a success message if the password is changed successfully.
     */
    @PutMapping("/updatePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody final PasswordRequest passwordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            userService.changePassword(existingUser, passwordRequest);
            return ResponseEntity.ok().body("Password has been changed.");
        }

        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Changes the inclusion of Two-Factor Authentication (2FA).
     * This endpoint changes the inclusion of Two-Factor Authentication (2FA) for the authenticated user.
     * It retrieves the authenticated user and updates the 2FA inclusion accordingly.
     *
     * @param twoFactorAuthenticationEnable A boolean value indicating whether to enable or disable 2FA.
     * @return ResponseEntity with status 200 OK and the updated 2FA inclusion status if the operation is successful.
     */
    @PutMapping("/changeOf2FAInclusion")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changeOf2FAInclusion(@RequestParam boolean twoFactorAuthenticationEnable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User existingUser = userService.validateAndGetUserByUsername(userDetails.getUsername());
            return ResponseEntity.ok().body(userService.changeOf2FAInclusion(existingUser, twoFactorAuthenticationEnable));
        }

        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Handles the process of resetting a user's password.
     *
     * @param passwordRequest The request object containing the new password.
     * @param email The email associated with the user's account.
     * @return The updated user object after the password reset, or null if the process failed.
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordRequest passwordRequest, @RequestParam String email) {
        userService.changePasswordFromResetLink(passwordRequest, email);
        return ResponseEntity.ok().build();
    }
}
