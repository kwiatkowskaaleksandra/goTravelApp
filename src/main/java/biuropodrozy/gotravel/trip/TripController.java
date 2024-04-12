package biuropodrozy.gotravel.trip;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.trip.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.trip.dto.response.DeepLResponse;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The type Trip controller.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/trips")
public class TripController {

    /**
     * Service for managing Trip-related operations.
     */
    private final TripService tripService;

    /**
     * Helper class for authentication.
     */
    private final AuthenticationHelper authenticationHelper;

    /**
     * Retrieves the trip by its ID and selected language.
     *
     * @param idTrip       The ID of the trip to retrieve.
     * @param selectedLang The selected language for the trip description.
     * @return ResponseEntity containing the trip with translated description if the language is "GB",
     *         otherwise returns the trip without translation.
     */
    @GetMapping("/{idTrip}/{selectedLang}")
    ResponseEntity<Trip> readTripById(@PathVariable final Long idTrip, @PathVariable String selectedLang) {
        Trip trip = tripService.getTripByIdTrip(idTrip);
        if (selectedLang.equals("GB")) {
            String translate = translate(trip.getTripDescription());
            trip.setTripDescription(translate);
        }
        return ResponseEntity.ok(trip);
    }

    /**
     * Translates the given text using the DeepL translation API.
     *
     * @param text The text to be translated.
     * @return The translated text.
     */
    @PostMapping("/translate")
    public String translate(String text) {
        final String url = "https://api-free.deepl.com/v2/translate";
        final String apiKey = "1bbf5502-ab39-7243-10a1-8663e99ca3af:fx";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "DeepL-Auth-Key " + apiKey);
        headers.set("Content-Type", "application/json");

        String body = "{\"text\": [\"" + text + "\"], \"target_lang\": \"EN\"}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<DeepLResponse> response = restTemplate.postForEntity(url, entity, DeepLResponse.class);
            if (response.getBody() != null && !response.getBody().getTranslations().isEmpty()) {
                return response.getBody().getTranslations().get(0).getText();
            } else {
                log.error("No translation found.");
                return "No translation found.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Translation error.");
            return "Translation error.";
        }
    }

    /**
     * Retrieves all trips of a specific type with pagination.
     *
     * @param typeOfTrip The type of trip to filter.
     * @param page       The page number.
     * @param size       The number of trips per page.
     * @return ResponseEntity containing a page of trips of the specified type.
     */
    @GetMapping(value = "/all/{typeOfTrip}")
    ResponseEntity<Page<Trip>> readAllTrips(@PathVariable String typeOfTrip,
                                            @RequestParam int page,
                                            @RequestParam int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(tripService.getTripsByTypeOfTrip(typeOfTrip, paging));
    }

    /**
     * Counts the number of trips of a specific type.
     *
     * @param typeOfTrip The type of trip to count.
     * @return ResponseEntity containing the count of trips of the specified type.
     */
    @GetMapping(value = "/countTrips/{typeOfTrip}")
    ResponseEntity<Integer> countTripsByTypeOfTrip(@PathVariable final String typeOfTrip) {
        return ResponseEntity.ok(tripService.countTripByTypeOfTrip(typeOfTrip));
    }

    /**
     * Filters trips based on specified criteria like type of trip, country, transport type, price range, and number of days.
     *
     * @param typeOfTrip      The type of trip to filter.
     * @param idCountry       The ID of the country to filter.
     * @param typeOfTransport The type of transport to filter.
     * @param minPrice        The minimum price to filter.
     * @param maxPrice        The maximum price to filter.
     * @param minDays         The minimum number of days to filter.
     * @param maxDays         The maximum number of days to filter.
     * @param page            The page number.
     * @param size            The number of trips per page.
     * @return ResponseEntity containing a page of filtered trips based on the specified criteria.
     */
    @GetMapping("/findByFilters")
    ResponseEntity<Page<Trip>> filterByCountryTransportNumberOfDays(@RequestParam(required = false) String typeOfTrip,
                                                                    @RequestParam(required = false) Integer idCountry,
                                                                    @RequestParam(required = false) Integer typeOfTransport,
                                                                    @RequestParam(required = false) Double minPrice,
                                                                    @RequestParam(required = false) Double maxPrice,
                                                                    @RequestParam(required = false) Integer minDays,
                                                                    @RequestParam(required = false) Integer maxDays,
                                                                    @RequestParam(required = false) int page,
                                                                    @RequestParam(required = false) int size) {
        Pageable paging = PageRequest.of(page, size);
        TripFilteringRequest tripFilteringRequest = new TripFilteringRequest(typeOfTrip, idCountry, typeOfTransport, minPrice, maxPrice, minDays, maxDays);
        Page<Trip> trips = tripService.filteringTrips(tripFilteringRequest, paging);

        return ResponseEntity.ok(trips);
    }

    /**
     * Provides trip recommendations based on user preferences.
     *
     * @param userPreferences the user trip preferences
     * @return ResponseEntity containing the trip recommendations
     */
    @PostMapping("/tripRecommendation")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> tripRecommendation(@RequestBody UserTripPreferences userPreferences) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            return ResponseEntity.ok().body(tripService.tripRecommendation(userPreferences));
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves the most booked trips.
     *
     * @return ResponseEntity containing the list of most booked trips
     */
    @GetMapping("/mostBookedTrips")
    ResponseEntity<List<Trip>> getMostBookedTrips() {
        return ResponseEntity.ok().body(tripService.getMostBookedTrips());
    }

    /**
     * Validates a trip.
     * Only accessible to users with the role 'MODERATOR'.
     *
     * @param trip the trip to be validated
     * @return ResponseEntity containing the validation result
     */
    @PostMapping("/validate")
    @PreAuthorize("hasRole('MODERATOR')")
    ResponseEntity<?> validate(@RequestBody Trip trip) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            return ResponseEntity.ok().body(tripService.validate(trip));
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Saves a new trip.
     * Only accessible to users with the role 'MODERATOR'.
     *
     * @param trip the trip to be saved
     * @return ResponseEntity indicating success or failure of the operation
     */
    @PostMapping("/saveNewTrip")
    @PreAuthorize("hasRole('MODERATOR')")
    ResponseEntity<?> saveNewTrip(@RequestBody Trip trip) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            tripService.saveTrip(trip);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Deletes an offer.
     * Only accessible to users with the role 'MODERATOR'.
     *
     * @param idTrip the ID of the trip to be deleted
     * @return ResponseEntity indicating success or failure of the operation
     */
    @DeleteMapping("/deleteTheOffer/{idTrip}")
    @PreAuthorize("hasRole('MODERATOR')")
    ResponseEntity<?> deleteTheOffer(@PathVariable Long idTrip) {
        User authenticationUser = authenticationHelper.validateAuthentication();
        if (authenticationUser != null) {
            tripService.deleteTheOffer(idTrip);
            return ResponseEntity.ok().build();
        }
        log.error("Unauthorized access.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
