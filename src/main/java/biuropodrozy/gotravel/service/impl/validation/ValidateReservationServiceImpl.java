package biuropodrozy.gotravel.service.impl.validation;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Service class responsible for validating reservation data.
 */
@Slf4j
@Service
public class ValidateReservationServiceImpl {

    /**
     * Validates the provided reservation data.
     *
     * @param data The reservation data to validate.
     * @throws UserException         Thrown if the user has incomplete personal data.
     * @throws ReservationException  Thrown if reservation data is incomplete or invalid.
     */
    public void validateReservationData(ValidationData data) {
        if (userHasIncompletePersonalData(data.getUser())) {
            log.error("Please complete all personal data.");
            throw new UserException("pleaseCompleteAllPersonalData");
        }
        if (data.getIdCity() == 0 || data.getIdAccommodation() == 0 || data.getNumberOfDays() == 0) {
            log.error("Please complete the country, city, accommodation and number of days information for your trip.");
            throw new ReservationException("pleaseCompleteTheCountryCityAndAccommodationInformationForYourTrip");
        }
        if (data.getNumberOfChildren() <= 0 && data.getNumberOfAdults() <= 0) {
            log.error("Please complete the information about the number of people traveling.");
            throw new ReservationException("pleaseCompleteTheInformationAboutTheNumberOfPeopleTraveling");
        } else if (data.getNumberOfChildren() != 0 && data.getNumberOfAdults() == 0) {
            log.error("Persons under 18 years of age cannot travel without an adult guardian.");
            throw new ReservationException("personsUnder18YearsOfAgeCannotTravelWithoutAnAdultGuardian");
        }
        if (data.getDepartureDate() == null) {
            log.error("Please provide your departure date.");
            throw new ReservationException("pleaseProvideYourDepartureDate");
        }
    }

    /**
     * Checks if the user has incomplete personal data.
     *
     * @param user The user whose personal data is to be checked.
     * @return True if the user has incomplete personal data, otherwise false.
     */
    private boolean userHasIncompletePersonalData(User user) {
        return Stream.of(user.getUsername(), user.getEmail(), user.getStreet(), user.getCity(), user.getZipCode(), user.getLastname(), user.getFirstname(), user.getPhoneNumber(), user.getStreetNumber())
                .anyMatch(Objects::isNull);
    }

}
