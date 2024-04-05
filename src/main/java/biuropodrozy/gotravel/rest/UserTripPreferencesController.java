package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.UserTripPreferences;
import biuropodrozy.gotravel.service.UserTripPreferencesService;
import biuropodrozy.gotravel.service.impl.AuthenticationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user trip preferences.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/userTripPreferences")
public class UserTripPreferencesController {

    /**
     * Helper class for authentication.
     */
    private final AuthenticationHelper authenticationHelper;

    /**
     * Service class for managing user trip preferences.
     */
    private final UserTripPreferencesService userTripPreferencesService;

    /**
     * Retrieves the trip preferences for the authenticated user.
     *
     * @return ResponseEntity containing the user trip preferences
     */
    @GetMapping("/getPreferences")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getPreferences() {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            UserTripPreferences preferences = userTripPreferencesService.getUserTripPreferences(authenticationUser);
            return ResponseEntity.ok().body(preferences);
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Saves the trip preferences for the authenticated user.
     *
     * @param userTripPreferences the user trip preferences to save
     * @return ResponseEntity containing the saved user trip preferences
     */
    @PostMapping("/savePreferences")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> savePreferences(@RequestBody UserTripPreferences userTripPreferences) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            UserTripPreferences preferences = userTripPreferencesService.savePreferences(authenticationUser, userTripPreferences);
            return ResponseEntity.ok().body(preferences);
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
