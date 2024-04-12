package biuropodrozy.gotravel.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request object for filtering trips.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripFilteringRequest {

    /**
     * The type of trip.
     */
    private String typeOfTrip;

    /**
     * The ID of the country.
     */
    private int idCountry;

    /**
     * The type of transport.
     */
    private int typeOfTransport;

    /**
     * The minimum price.
     */
    private double minPrice;

    /**
     * The maximum price.
     */
    private double maxPrice;

    /**
     * The minimum number of days.
     */
    private int minDays;

    /**
     * The maximum number of days.
     */
    private int maxDays;
}