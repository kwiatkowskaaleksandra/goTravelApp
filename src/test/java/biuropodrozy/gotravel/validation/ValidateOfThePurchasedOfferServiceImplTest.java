package biuropodrozy.gotravel.validation;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.security.oauth2.OAuth2Provider;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidateOfThePurchasedOfferServiceImplTest {

    @InjectMocks
    private ValidateOfThePurchasedOfferServiceImpl validateOfThePurchasedOfferService;
    private ValidationData validationData;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "user123", "password123", "User", "User", "user@example.com", "123456789", "Kielce",
                "Warszawska", "12a", "12345", null, null, null, null, null,
                null, false, null, true, OAuth2Provider.LOCAL, null);
        validationData = new ValidationData(user, 1, 1, LocalDate.now(), 1, 1, 1);
    }

    @Test
    void shouldThrowExceptionWhenPersonalDataIsIncomplete() {
        user.setFirstname(null);

        Exception exception = assertThrows(UserException.class, () -> validateOfThePurchasedOfferService.validateReservationData(validationData));
        assertEquals("pleaseCompleteAllPersonalData", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTravelDetailsAreIncomplete() {
        validationData.setIdAccommodation(0);

        Exception exception = assertThrows(ReservationException.class, () -> validateOfThePurchasedOfferService.validateReservationData(validationData));
        assertEquals("pleaseCompleteTheCountryCityAndAccommodationInformationForYourTrip", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNoTravelersAreSpecified() {
        validationData.setNumberOfAdults(0);
        validationData.setNumberOfChildren(0);

        Exception exception = assertThrows(ReservationException.class, () -> validateOfThePurchasedOfferService.validateReservationData(validationData));
        assertEquals("pleaseCompleteTheInformationAboutTheNumberOfPeopleTraveling", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenChildrenTravelWithoutAdults() {
        validationData.setNumberOfAdults(0);
        validationData.setNumberOfChildren(1);

        Exception exception = assertThrows(ReservationException.class, () -> validateOfThePurchasedOfferService.validateReservationData(validationData));
        assertEquals("personsUnder18YearsOfAgeCannotTravelWithoutAnAdultGuardian", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDepartureDateIsMissing() {
        validationData.setDepartureDate(null);

        Exception exception = assertThrows(ReservationException.class, () -> validateOfThePurchasedOfferService.validateReservationData(validationData));
        assertEquals("pleaseProvideYourDepartureDate", exception.getMessage());
    }
}