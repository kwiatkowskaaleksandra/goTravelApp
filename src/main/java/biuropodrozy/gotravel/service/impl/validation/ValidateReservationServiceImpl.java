package biuropodrozy.gotravel.service.impl.validation;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
public class ValidateReservationServiceImpl {

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

    private boolean userHasIncompletePersonalData(User user) {
        return Stream.of(user.getUsername(), user.getEmail(), user.getStreet(), user.getCity(), user.getZipCode(), user.getLastname(), user.getFirstname(), user.getPhoneNumber(), user.getStreetNumber())
                .anyMatch(Objects::isNull);
    }

}
