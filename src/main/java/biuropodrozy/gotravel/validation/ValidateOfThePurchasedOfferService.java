package biuropodrozy.gotravel.validation;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.exception.UserException;

/**
 * Service interface for validating purchased offers.
 */
public interface ValidateOfThePurchasedOfferService {

    /**
     * Validates the provided reservation data.
     *
     * @param data The reservation data to validate.
     * @throws UserException         Thrown if the user has incomplete personal data.
     * @throws ReservationException  Thrown if reservation data is incomplete or invalid.
     */
    void validateReservationData(ValidationData data);

}
