package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Trip controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trips")
public class TripController {

    /**
     * Service for managing Trip-related operations.
     */
    private final TripService tripService;

    /**
     * Maximum price value.
     */
    private static final int MAX_PRICE = 100000000;

    /**
     * Maximum number of days value.
     */
    private static final int MAX_DAYS = 10000;

    /**
     * Read trip by id trip response entity.
     *
     * @param idTrip the id trip
     * @return the response entity
     */
    @GetMapping("/{idTrip}")
    ResponseEntity<Trip> readTripById(@PathVariable final Long idTrip) {
        return ResponseEntity.ok(tripService.getTripByIdTrip(idTrip));
    }

    /**
     * Read all trips response entity by type of trip.
     *
     * @param typeOfTrip the type of trip
     * @return list of trips response entity
     */
    @GetMapping(value = "/all/{typeOfTrip}")
    ResponseEntity<List<Trip>> readAllTrips(@PathVariable final String typeOfTrip) {
        return ResponseEntity.ok(tripService.getTripsByTypeOfTrip(typeOfTrip));
    }

    /**
     * Get trips by filter response entity.
     *
     * @param idCountry the id country
     * @param typeOfTransport the type of transport
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips response entity
     */
    @GetMapping("/findByValues/{idCountry}/{typeOfTransport}/{minDays}/{maxDays}")
    ResponseEntity<List<Trip>> filterByCountryTransportNumberOfDays(@PathVariable(required = false) final int idCountry, @PathVariable(required = false) final int typeOfTransport,
                                                                    @PathVariable(required = false) final int minDays, @PathVariable(required = false) final int maxDays) {

        List<Trip> tripList = tripService.getAllTrips();

        if (idCountry != 0) {
            tripList = tripList.stream().filter(c -> c.getTripCity().getCountry().getIdCountry() == idCountry).collect(Collectors.toList());
        }
        if (typeOfTransport != 0) {
            tripList =
                    tripList.stream().filter(c -> c.getTripTransport().getIdTransport() == typeOfTransport).collect(Collectors.toList());
        }
        if (minDays != 0 || maxDays != 0) {
            if (minDays != 0 && maxDays == 0) {
                tripList =
                        tripList.stream().filter(c -> c.getNumberOfDays() >= minDays && c.getNumberOfDays() <= MAX_DAYS).collect(Collectors.toList());
            } else {
                tripList =
                        tripList.stream().filter(c -> c.getNumberOfDays() >= minDays && c.getNumberOfDays() <= maxDays).collect(Collectors.toList());
            }
        }
        return ResponseEntity.ok(tripList);
    }

    /**
     * Get trips by filter response entity.
     *
     * @param typeOfTrip the type of trip
     * @param idCountry the id country
     * @param typeOfTransport the type of transport
     * @param minPrice the minimal price
     * @param maxPrice the maximum price
     * @param minDays the minimal number of days
     * @param maxDays the maximum number of days
     * @return list of trips response entity
     */
    @GetMapping(value = "/findByFilters/{typeOfTrip}/{idCountry}/{typeOfTransport}/{minPrice}/{maxPrice}/{minDays}/{maxDays}")
    ResponseEntity<List<Trip>> filterTripByTypeOfTripCountryTransportPriceNumberOfDays(@PathVariable final String typeOfTrip, @PathVariable(required = false) final int idCountry, @PathVariable(required = false) final int typeOfTransport,
                                                                                       @PathVariable(required = false) final double minPrice, @PathVariable(required = false) final double maxPrice,
                                                                                       @PathVariable(required = false) final int minDays, @PathVariable(required = false) final int maxDays) {

        List<Trip> tripList = tripService.getTripsByTypeOfTrip(typeOfTrip);

        if (idCountry != 0) {
            tripList = tripList.stream().filter(c -> c.getTripCity().getCountry().getIdCountry() == idCountry).collect(Collectors.toList());
        }
        if (typeOfTransport != 0) {
            tripList =
                    tripList.stream().filter(c -> c.getTripTransport().getIdTransport() == typeOfTransport).collect(Collectors.toList());
        }
        if (minPrice != 0 || maxPrice != 0) {
            if (minPrice != 0 && maxPrice == 0) {
                tripList =
                        tripList.stream().filter(c -> c.getPrice() >= minPrice && c.getPrice() <= MAX_PRICE).collect(Collectors.toList());
            } else {
                tripList =
                        tripList.stream().filter(c -> c.getPrice() >= minPrice && c.getPrice() <= maxPrice).collect(Collectors.toList());
            }
        }
        if (minDays != 0 || maxDays != 0) {
            if (minDays != 0 && maxDays == 0) {
                tripList =
                        tripList.stream().filter(c -> c.getNumberOfDays() >= minDays && c.getNumberOfDays() <= MAX_DAYS).collect(Collectors.toList());
            } else {
                tripList =
                        tripList.stream().filter(c -> c.getNumberOfDays() >= minDays && c.getNumberOfDays() <= maxDays).collect(Collectors.toList());
            }
        }
        return ResponseEntity.ok(tripList);
    }
}
