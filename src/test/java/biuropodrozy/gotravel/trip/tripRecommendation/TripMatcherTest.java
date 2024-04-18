package biuropodrozy.gotravel.trip.tripRecommendation;

import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripMatcherTest {

    @InjectMocks
    private TripMatcher tripMatcher;

    private Trip trip;
    private UserTripPreferences preferences;

    @BeforeEach
    void setUp() {
        trip = new Trip();
        preferences = new UserTripPreferences();
    }

    @Test
    void matchTripWithScore_VeryHighMatch_ReturnsTrue() {
        trip.setPrice(400);
        trip.setActivityLevel(3);
        trip.setNumberOfDays(7);
        trip.setTripDescription("safari przygoda dżungla");
        trip.setFood("dinner");

        preferences.setPriceLevel(9);
        preferences.setActivityLevel(3);
        preferences.setDuration(7);
        preferences.setTripType(5.0); // Assuming 5.0 corresponds to a "safari"
        preferences.setFood(3.0); // Assuming 3.0 corresponds to "dinner"

        boolean result = tripMatcher.matchTripWithScore(trip, 100, 400, 85, preferences);
        assertTrue(result);
    }

    @Test
    void matchTripWithScore_HighMatch_ReturnsTrue() {
        trip.setPrice(410);
        trip.setActivityLevel(4);
        trip.setNumberOfDays(10);
        trip.setTripDescription("góry wspinaczki i szlaki");
        trip.setFood("breakfastDinner");

        preferences.setPriceLevel(2);
        preferences.setActivityLevel(5);
        preferences.setDuration(9);
        preferences.setTripType(0.5); // Assuming 0.5 corresponds to "mountain"
        preferences.setFood(6.0); // Assuming 6.0 corresponds to "breakfastDinner"

        boolean result = tripMatcher.matchTripWithScore(trip, 400, 600, 65, preferences);
        assertTrue(result);
    }

    @Test
    void matchTripWithScore_VeryLowMatch_ReturnsFalse() {
        trip.setPrice(1000);
        trip.setActivityLevel(1);
        trip.setNumberOfDays(1);
        trip.setTripDescription("city tour and museum visits");
        trip.setFood("none");

        preferences.setPriceLevel(5);
        preferences.setActivityLevel(1);
        preferences.setDuration(2);
        preferences.setTripType(4.0); // Assuming 4.0 corresponds to "city"
        preferences.setFood(0.5); // Assuming 0.5 corresponds to "none"

        boolean result = tripMatcher.matchTripWithScore(trip, 900, 1100, 15, preferences);
        assertFalse(result);
    }

    @Test
    void matchTripWithScore_VeryLowMatch_ReturnsTrue() {
        trip.setPrice(1050);
        trip.setActivityLevel(1);
        trip.setNumberOfDays(1);
        trip.setTripDescription("architektura muzeum metropolia katedra zabytek");
        trip.setFood("supper");

        preferences.setPriceLevel(9);
        preferences.setActivityLevel(1);
        preferences.setDuration(2);
        preferences.setTripType(4.0); // Assuming 4.0 corresponds to "city"
        preferences.setFood(4.5); // Assuming 0.5 corresponds to "supper"

        boolean result = tripMatcher.matchTripWithScore(trip, 900, 1100, 15, preferences);
        assertTrue(result);
    }

    @Test
    void matchTripWithScore_LowMatch_ReturnsTrue() {
        trip.setPrice(750);
        trip.setActivityLevel(1);
        trip.setNumberOfDays(1);
        trip.setTripDescription("architektura muzeum metropolia katedra zabytek");
        trip.setFood("breakfastSupper");

        preferences.setPriceLevel(5);
        preferences.setActivityLevel(1);
        preferences.setDuration(2);
        preferences.setTripType(4.0); // Assuming 4.0 corresponds to "city"
        preferences.setFood(7.5); // Assuming 0.5 corresponds to "breakfastSupper"

        boolean result = tripMatcher.matchTripWithScore(trip, 600, 1100, 25, preferences);
        assertTrue(result);
    }

    @Test
    void matchTripWithScore_LowMatch_ReturnsFalse() {
        trip.setPrice(750);
        trip.setActivityLevel(1);
        trip.setNumberOfDays(1);
        trip.setTripDescription("Wakacje i pełny wypoczynek");
        trip.setFood("breakfast");

        preferences.setPriceLevel(5);
        preferences.setActivityLevel(1);
        preferences.setDuration(2);
        preferences.setTripType(5.0); // Assuming 4.0 corresponds to "safari"
        preferences.setFood(1.5); // Assuming 0.5 corresponds to "breakfast"

        boolean result = tripMatcher.matchTripWithScore(trip, 600, 1100, 25, preferences);
        assertFalse(result);
    }

    @Test
    void matchTripWithScore_MediumMatch_ReturnsTrue() {
        trip.setPrice(750);
        trip.setActivityLevel(1);
        trip.setNumberOfDays(1);
        trip.setTripDescription("port kapitan żegluga flota rejs");
        trip.setFood("dinnerSupper");

        preferences.setPriceLevel(5);
        preferences.setActivityLevel(1);
        preferences.setDuration(2);
        preferences.setTripType(3.0); // Assuming 3.0 corresponds to "rejs"
        preferences.setFood(9.0); // Assuming 0.5 corresponds to "dinnerSupper"

        boolean result = tripMatcher.matchTripWithScore(trip, 600, 1100, 45, preferences);
        assertTrue(result);
    }

    @Test
    void matchTripWithScore_MediumMatch_ReturnsFalse() {
        trip.setPrice(120);
        trip.setActivityLevel(1);
        trip.setNumberOfDays(1);
        trip.setTripDescription("morze surfing windsurfing plaża rejs");
        trip.setFood("breakfastDinnerSupper");

        preferences.setPriceLevel(5);
        preferences.setActivityLevel(1);
        preferences.setDuration(2);
        preferences.setTripType(3.0); // Assuming 3.0 corresponds to "morze"
        preferences.setFood(9.9); // Assuming 0.5 corresponds to "breakfastDinnerSupper"

        boolean result = tripMatcher.matchTripWithScore(trip, 600, 1100, 45, preferences);
        assertFalse(result);
    }

}