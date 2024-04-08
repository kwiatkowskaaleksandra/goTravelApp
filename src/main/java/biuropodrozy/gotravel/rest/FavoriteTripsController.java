package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.FavoriteTripsService;
import biuropodrozy.gotravel.service.impl.AuthenticationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing favorite trips-related.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/favoriteTrips")
public class FavoriteTripsController {

    /**
     * Helper class for authentication.
     */
    private final AuthenticationHelper authenticationHelper;

    /**
     * Service component for favorite trips-related operations.
     */
    private final FavoriteTripsService favoriteTripsService;

    /**
     * Adds a trip to favorites for the authenticated user.
     *
     * @param idTrip The ID of the trip to add to favorites.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/addToFavorites")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> addToFavorites(@RequestParam Long idTrip) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            favoriteTripsService.addToFavorites(authenticationUser, idTrip);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves the favorite trips for the authenticated user.
     *
     * @return ResponseEntity containing the favorite trips of the authenticated user.
     */
    @GetMapping("/getFavoritesTrips")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getFavoritesTrips() {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            return ResponseEntity.ok().body(favoriteTripsService.getFavoritesTrips(authenticationUser));
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Removes a trip from favorites for the authenticated user.
     *
     * @param idTrip The ID of the trip to remove from favorites.
     * @return ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("/removeTripFromFavorites")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> removeTripFromFavorites(@RequestParam Long idTrip) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
             favoriteTripsService.removeTripFromFavorites(authenticationUser, idTrip);
             return ResponseEntity.ok().build();
          }
          log.error("Unauthorized access.");
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
