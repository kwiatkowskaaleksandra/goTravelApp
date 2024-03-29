package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.rest.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.rest.dto.response.DeepLResponse;
import biuropodrozy.gotravel.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

}
