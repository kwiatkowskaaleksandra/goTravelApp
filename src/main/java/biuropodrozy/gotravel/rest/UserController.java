package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.request.PasswordRequest;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
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
     * Delete user response entity.
     *
     * @return the response entity
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
     * Update user info response entity.
     * @param user the user
     * @return the response entity
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
     * Update password response entity.
     *
     * @return the user response entity
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
}
