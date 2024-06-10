package biuropodrozy.gotravel.trip.tripRecommendation;

import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.net.URL;

/**
 * Service for evaluating user preferences using fuzzy logic.
 */
@Service
public class FuzzyLogicService {

    /**
     * Evaluates user preferences using fuzzy logic.
     *
     * @param preferences the user trip preferences
     * @return the evaluation score
     */
    public double evaluateUserPreferences(UserTripPreferences preferences) {
//        System.setProperty("java.awt.headless", "false");

        URL url = getClass().getClassLoader().getResource("preferencesEvaluation.fcl");
        assert url != null;
        String fileName = url.getPath();
        FIS fis = FIS.load(fileName, true);

        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return -1;
        }

//        if (!GraphicsEnvironment.isHeadless()) {
//            JFuzzyChart.get().chart(fis);
//        }
        fis.setVariable("activityLevel", preferences.getActivityLevel());
        fis.setVariable("priceLevel", preferences.getPriceLevel());
        fis.setVariable("duration", preferences.getDuration());
        fis.setVariable("tripType", preferences.getTripType());
        fis.setVariable("food", preferences.getFood());

        fis.evaluate();

        return fis.getVariable("recommendationScore").getValue();
    }
}
