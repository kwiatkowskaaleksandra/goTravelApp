package biuropodrozy.gotravel.validation;

import biuropodrozy.gotravel.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents data used for validating reservations.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ValidationData {

    /**
     * The user associated with the reservation.
     */
    private User user;

    /**
     * The number of children included in the reservation.
     */
    private int numberOfChildren;

    /**
     * The number of adults included in the reservation.
     */
    private int numberOfAdults;

    /**
     * The departure date for the reservation.
     */
    private LocalDate departureDate;

    /**
     * The ID of the accommodation for the reservation.
     */
    private int idAccommodation;

    /**
     * The ID of the city for the reservation.
     */
    private int idCity;

    /**
     * The number of days for the reservation.
     */
    private int numberOfDays;
}