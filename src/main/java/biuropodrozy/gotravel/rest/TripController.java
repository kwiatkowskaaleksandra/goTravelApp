package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.service.TransportService;
import biuropodrozy.gotravel.service.TripService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trips")
public class TripController {

    public static final Logger logger = LoggerFactory.getLogger(TripController.class);
    private final TripService tripService;
    private final TransportService transportService;
    private static final int MAX_PRICE = 100000000;
    private static final int MAX_DAYS = 10000;

    @GetMapping("/{idTrip}")
    ResponseEntity<Trip> readTripById(@PathVariable Long idTrip) {
        return ResponseEntity.ok(tripService.getTripByIdTrip(idTrip));
    }

    @GetMapping(value = "/all/{typeOfTrip}")
    ResponseEntity<List<Trip>> readAllTrips(@PathVariable String typeOfTrip) {
        return ResponseEntity.ok(tripService.getTripsByTypeOfTrip(typeOfTrip));
    }

    @GetMapping("/findByValues/{idCountry}/{typeOfTransport}/{minDays}/{maxDays}")
    ResponseEntity<List<Trip>> filterByCountryTransportNumberOfDays(@PathVariable(required = false) int idCountry, @PathVariable(required = false) int typeOfTransport,
                                                                    @PathVariable(required = false) int minDays, @PathVariable(required = false) int maxDays) {

        if (idCountry != 0 && typeOfTransport != 0 && minDays != 0 && maxDays != 0) {
            Transport current = transportService.getTransportById(typeOfTransport);
            return ResponseEntity.ok(tripService.getTripsByTripCity_Country_IdCountryAndTripTransportAndNumberOfDaysBetween(idCountry, current, minDays, maxDays));
        } else if (idCountry != 0) {
            return ResponseEntity.ok(tripService.getTripsByTripCity_Country_IdCountry(idCountry));
        } else if (typeOfTransport != 0) {
            Transport current = transportService.getTransportById(typeOfTransport);
            return ResponseEntity.ok(tripService.getTripsByTripTransport(current));
        } else if (minDays != 0 || maxDays != 0) {
            if (minDays != 0 && maxDays == 0) {
                return ResponseEntity.ok(tripService.getTripsByNumberOfDaysBetween(minDays, MAX_DAYS));
            } else {
                return ResponseEntity.ok(tripService.getTripsByNumberOfDaysBetween(0, maxDays));
            }
        } else {
            return ResponseEntity.ok(tripService.getAllTrips());
        }
    }

    @GetMapping(value = "/findByFilters/{typeOfTrip}/{idCountry}/{typeOfTransport}/{minPrice}/{maxPrice}/{minDays}/{maxDays}")
    ResponseEntity<List<Trip>> filterTripByTypeOfTripCountryTransportPriceNumberOfDays(@PathVariable String typeOfTrip, @PathVariable(required = false) int idCountry, @PathVariable(required = false) int typeOfTransport,
                                                                                       @PathVariable(required = false) double minPrice, @PathVariable(required = false) double maxPrice,
                                                                                       @PathVariable(required = false) int minDays, @PathVariable(required = false) int maxDays) {


        if (idCountry != 0 && typeOfTransport != 0 && minPrice != 0 && maxPrice != 0 && minDays != 0 && maxDays != 0) {
            Transport current = transportService.getTransportById(typeOfTransport);
            return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndTripCity_Country_IdCountryAndTripTransportAndPriceBetweenAndNumberOfDaysBetween(typeOfTrip, idCountry, current, minPrice, maxPrice, minDays, maxDays));
        } else if (idCountry != 0) {
            return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndTripCity_Country_IdCountry(typeOfTrip, idCountry));
        } else if (typeOfTransport != 0) {
            Transport current = transportService.getTransportById(typeOfTransport);
            return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndTripTransport(typeOfTrip, current));
        } else if (minPrice != 0 || maxPrice != 0) {
            if (minPrice != 0 && maxPrice == 0) {
                return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndPriceBetween(typeOfTrip, minPrice, MAX_PRICE));
            } else {
                return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndPriceBetween(typeOfTrip, 0, maxPrice));
            }
        } else if (minDays != 0 || maxDays != 0) {
            if (minDays != 0 && maxDays == 0) {
                return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndNumberOfDaysBetween(typeOfTrip, minDays, MAX_DAYS));
            } else {
                return ResponseEntity.ok(tripService.getTripsByTypeOfTripAndNumberOfDaysBetween(typeOfTrip, 0, maxDays));
            }
        } else {
            return ResponseEntity.ok(tripService.getTripsByTypeOfTrip(typeOfTrip));
        }
    }

}
