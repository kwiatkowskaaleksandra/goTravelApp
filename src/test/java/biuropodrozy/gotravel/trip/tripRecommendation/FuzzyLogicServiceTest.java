package biuropodrozy.gotravel.trip.tripRecommendation;

import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FuzzyLogicServiceTest {

    @Mock
    private ClassLoader classLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void evaluateUserPreferences_ShouldReturnScore_WhenFclFileIsLoaded() throws Exception {
        URL mockUrl = new URL("file:/C:/Users/kola8/Desktop/goTravelApp/target/classes/preferencesEvaluation.fcl");
        when(classLoader.getResource("preferencesEvaluation.fcl")).thenReturn(mockUrl);

        try (MockedStatic<FIS> mockedFIS = mockStatic(FIS.class)) {
            FIS fis = mock(FIS.class);
            mockedFIS.when(() -> FIS.load(mockUrl.getPath(), true)).thenReturn(fis);

            Variable recommendationScore = mock(Variable.class);
            when(fis.getVariable("recommendationScore")).thenReturn(recommendationScore);
            when(recommendationScore.getValue()).thenReturn(85.0);

            doNothing().when(fis).setVariable("activityLevel", 5);
            doNothing().when(fis).setVariable("priceLevel", 3);
            doNothing().when(fis).setVariable("duration", 10);
            doNothing().when(fis).setVariable("tripType", 1);
            doNothing().when(fis).setVariable("food", 2);

            doNothing().when(fis).evaluate();

            FuzzyLogicService fuzzyLogicService = new FuzzyLogicService();
            UserTripPreferences preferences = new UserTripPreferences(1L, 1, 2, 1, 2, 3, new User());

            double score = fuzzyLogicService.evaluateUserPreferences(preferences);

            assertEquals(85.0, score, 0.001);
        }
    }

    @Test
    public void testFileNotFound() {
        try (MockedStatic<FIS> mockedFIS = mockStatic(FIS.class)) {
            UserTripPreferences preferences = new UserTripPreferences(1L, 1, 2, 1, 2, 3, new User());
            mockedFIS.when(() -> FIS.load(anyString(), eq(true))).thenReturn(null);

            FuzzyLogicService fuzzyLogicService = new FuzzyLogicService();
            double score = fuzzyLogicService.evaluateUserPreferences(preferences);

            assertEquals(-1.0, score, "File should not load and return -1");
        }
    }
}