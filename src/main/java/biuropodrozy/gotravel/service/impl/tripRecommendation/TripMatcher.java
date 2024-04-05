package biuropodrozy.gotravel.service.impl.tripRecommendation;

import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.UserTripPreferences;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Service for matching trips with user preferences.
 */
@Service
public class TripMatcher {

    /**
     * Matches a trip with user preferences based on score.
     *
     * @param trip            the trip to match
     * @param minPrice        the minimum price of the trip
     * @param maxPrice        the maximum price of the trip
     * @param score           the score of the trip
     * @param userPreferences the user trip preferences
     * @return true if the trip matches the user preferences, false otherwise
     */
    public boolean matchTripWithScore(Trip trip, double minPrice, double maxPrice, double score, UserTripPreferences userPreferences) {
        final double VERY_HIGH_MATCH_THRESHOLD = 80;
        final double HIGH_MATCH_THRESHOLD = 60;
        final double MODERATE_MATCH_THRESHOLD = 40;
        final double LOW_MATCH_THRESHOLD = 20;

        boolean isVeryHighMatch = score > VERY_HIGH_MATCH_THRESHOLD;
        boolean isHighMatch = score > HIGH_MATCH_THRESHOLD && score <= VERY_HIGH_MATCH_THRESHOLD;
        boolean isMediumMatch = score > MODERATE_MATCH_THRESHOLD && score <= HIGH_MATCH_THRESHOLD;
        boolean isLowMatch = score > LOW_MATCH_THRESHOLD && score <= MODERATE_MATCH_THRESHOLD;

        if (isVeryHighMatch) {
            return matchesVeryHighCriteria(trip, userPreferences, minPrice, maxPrice);
        } else if (isHighMatch) {
            return matchesHighCriteria(trip, userPreferences, minPrice, maxPrice);
        } else if (isMediumMatch) {
            return matchesMediumCriteria(trip, userPreferences, minPrice, maxPrice);
        } else if (isLowMatch) {
            return matchesLowCriteria(trip, userPreferences, minPrice, maxPrice);
        } else {
            return matchesVeryLowCriteria(trip, userPreferences, minPrice, maxPrice);
        }
    }

    /**
     * Determines if a trip matches very high criteria based on the specified user preferences, minimum price, and maximum price.
     * This method considers a trip to be a very high match if it closely aligns with the user preferences.
     *
     * @param trip          the trip to evaluate
     * @param preferences   the user trip preferences
     * @param minPrice      the minimum price of the trip range
     * @param maxPrice      the maximum price of the trip range
     * @return true if the trip meets very high criteria, false otherwise
     */
    public boolean matchesVeryHighCriteria(Trip trip, UserTripPreferences preferences, double minPrice, double maxPrice) {
        double tripPriceLevel = (trip.getPrice() - minPrice) / (maxPrice - minPrice) * 10;

        boolean matchesPrice = Math.abs(tripPriceLevel - preferences.getPriceLevel()) <= 1;
        boolean matchesActivity = Math.abs(trip.getActivityLevel() - preferences.getActivityLevel()) <= 1;
        boolean matchesDuration = Math.abs(trip.getNumberOfDays() - preferences.getDuration()) <= 1;
        boolean matchesTripType = determineTripType(trip.getTripDescription()) == preferences.getTripType();
        boolean matchesFood = matchFoodPreference(trip, preferences.getFood()) >= 0.5;

        return matchesPrice && matchesActivity && matchesDuration && matchesFood && matchesTripType;
    }

    /**
     * Determines if a trip matches high criteria based on the specified user preferences, minimum price, and maximum price.
     * This method considers a trip to be a very high match if it closely aligns with the user preferences.
     *
     * @param trip          the trip to evaluate
     * @param preferences   the user trip preferences
     * @param minPrice      the minimum price of the trip range
     * @param maxPrice      the maximum price of the trip range
     * @return true if the trip meets very high criteria, false otherwise
     */
    public boolean matchesHighCriteria(Trip trip, UserTripPreferences preferences, double minPrice, double maxPrice) {
        double tripPriceLevel = (trip.getPrice() - minPrice) / (maxPrice - minPrice) * 10;

        boolean matchesPrice = Math.abs(tripPriceLevel - preferences.getPriceLevel()) <= 2;
        boolean matchesActivity = Math.abs(trip.getActivityLevel() - preferences.getActivityLevel()) <= 2;
        boolean matchesDuration = Math.abs(trip.getNumberOfDays() - preferences.getDuration()) <= 2;
        boolean matchesTripType = determineTripType(trip.getTripDescription()) == preferences.getTripType();
        boolean matchesFood = matchFoodPreference(trip, preferences.getFood()) >= 0.5;

        return matchesPrice && matchesActivity && matchesDuration && matchesFood && matchesTripType;
    }

    /**
     * Determines if a trip matches medium criteria based on the specified user preferences, minimum price, and maximum price.
     * This method considers a trip to be a very high match if it closely aligns with the user preferences.
     *
     * @param trip          the trip to evaluate
     * @param preferences   the user trip preferences
     * @param minPrice      the minimum price of the trip range
     * @param maxPrice      the maximum price of the trip range
     * @return true if the trip meets very high criteria, false otherwise
     */
    public boolean matchesMediumCriteria(Trip trip, UserTripPreferences preferences, double minPrice, double maxPrice) {
        double tripPriceLevel = (trip.getPrice() - minPrice) / (maxPrice - minPrice) * 10;

        boolean matchesPrice = Math.abs(tripPriceLevel - preferences.getPriceLevel()) <= 3;
        boolean matchesActivity = Math.abs(trip.getActivityLevel() - preferences.getActivityLevel()) <= 3;
        boolean matchesDuration = Math.abs(trip.getNumberOfDays() - preferences.getDuration()) <= 3;
        boolean matchesTripType = determineTripType(trip.getTripDescription()) == preferences.getTripType();
        boolean matchesFood = matchFoodPreference(trip, preferences.getFood()) >= 0.5;

        return matchesPrice && matchesActivity && matchesDuration && matchesFood && matchesTripType;
    }

    /**
     * Determines if a trip matches low criteria based on the specified user preferences, minimum price, and maximum price.
     * This method considers a trip to be a very high match if it closely aligns with the user preferences.
     *
     * @param trip          the trip to evaluate
     * @param preferences   the user trip preferences
     * @param minPrice      the minimum price of the trip range
     * @param maxPrice      the maximum price of the trip range
     * @return true if the trip meets very high criteria, false otherwise
     */
    public boolean matchesLowCriteria(Trip trip, UserTripPreferences preferences, double minPrice, double maxPrice) {
        double tripPriceLevel = (trip.getPrice() - minPrice) / (maxPrice - minPrice) * 10;

        boolean matchesPrice = Math.abs(tripPriceLevel - preferences.getPriceLevel()) <= 4;
        boolean matchesActivity = Math.abs(trip.getActivityLevel() - preferences.getActivityLevel()) <= 4;
        boolean matchesDuration = Math.abs(trip.getNumberOfDays() - preferences.getDuration()) <= 4;
        boolean matchesTripType = determineTripType(trip.getTripDescription()) == preferences.getTripType();
        boolean matchesFood = matchFoodPreference(trip, preferences.getFood()) >= 0.5;

        return matchesPrice && matchesActivity && matchesDuration && matchesFood && matchesTripType;
    }

    /**
     * Determines if a trip matches very low criteria based on the specified user preferences, minimum price, and maximum price.
     * This method considers a trip to be a very high match if it closely aligns with the user preferences.
     *
     * @param trip          the trip to evaluate
     * @param preferences   the user trip preferences
     * @param minPrice      the minimum price of the trip range
     * @param maxPrice      the maximum price of the trip range
     * @return true if the trip meets very high criteria, false otherwise
     */
    public boolean matchesVeryLowCriteria(Trip trip, UserTripPreferences preferences, double minPrice, double maxPrice) {
        double tripPriceLevel = (trip.getPrice() - minPrice) / (maxPrice - minPrice) * 10;

        boolean matchesPrice = Math.abs(tripPriceLevel - preferences.getPriceLevel()) <= 5;
        boolean matchesActivity = Math.abs(trip.getActivityLevel() - preferences.getActivityLevel()) <= 5;
        boolean matchesDuration = Math.abs(trip.getNumberOfDays() - preferences.getDuration()) <= 5;
        boolean matchesTripType = determineTripType(trip.getTripDescription()) == preferences.getTripType();
        boolean matchesFood = matchFoodPreference(trip, preferences.getFood()) >= 0.5;

        return matchesPrice && matchesActivity && matchesDuration && matchesFood && matchesTripType;
    }

    /**
     * Determines the match score of a trip based on the food preference specified.
     *
     * @param trip            the trip to evaluate
     * @param foodPreference the food preference value
     * @return the match score of the trip based on the food preference
     */
    double matchFoodPreference(Trip trip, double foodPreference) {
        double matchScore = 0.0;

        String mappedFoodPreference = mapFoodPreferenceToLabel(foodPreference);

        switch (mappedFoodPreference) {
            case "breakfast" -> matchScore = trip.getFood().equals("breakfast") ? 1.0 : 0.0;
            case "dinner" -> matchScore = trip.getFood().equals("dinner") ? 1.0 : 0.0;
            case "supper" -> matchScore = trip.getFood().equals("supper") ? 1.0 : 0.0;
            case "breakfastDinner" -> {
                boolean hasBreakfastDinner = trip.getFood().equals("breakfastDinner");
                boolean hasBreakfast = trip.getFood().equals("breakfast");
                boolean hasDinner = trip.getFood().equals("dinner");
                matchScore = hasBreakfastDinner ? 1.0 : (hasBreakfast || hasDinner) ? 0.5 : 0.0;
            }
            case "breakfastSupper" -> {
                boolean hasBreakfastSupper = trip.getFood().equals("breakfastSupper");
                boolean hasBreakfast = trip.getFood().equals("breakfast");
                boolean hasSupper = trip.getFood().equals("supper");
                matchScore = hasBreakfastSupper ? 1.0 : (hasBreakfast || hasSupper) ? 0.5 : 0.0;
            }
            case "dinnerSupper" -> {
                boolean hasDinnerSupper = trip.getFood().equals("dinnerSupper");
                boolean hasDinner = trip.getFood().equals("dinner");
                boolean hasSupper = trip.getFood().equals("supper");
                matchScore = hasDinnerSupper ? 1.0 : (hasDinner || hasSupper) ? 0.5 : 0.0;
            }
            case "breakfastDinnerSupper" -> {
                boolean hasBreakfastDinnerSupper = trip.getFood().equals("breakfastDinnerSupper");
                boolean hasBreakfast = trip.getFood().contains("breakfast");
                boolean hasDinner = trip.getFood().contains("dinner");
                boolean hasSupper = trip.getFood().contains("supper");
                matchScore = hasBreakfastDinnerSupper ? 1.0 : (hasBreakfast || hasDinner || hasSupper) ? 0.5 : 0.0;
            }
            case "none" -> matchScore = trip.getFood().equals("none") ? 1.0 : 0.0;
        }

        return matchScore;
    }

    /**
     * Maps the food preference value to a descriptive label.
     *
     * @param foodPreference the food preference value to map
     * @return the descriptive label corresponding to the food preference value
     */
    private String mapFoodPreferenceToLabel(double foodPreference) {
        if (foodPreference <= 0.5) return "none";
        if (foodPreference <= 1.5) return "breakfast";
        if (foodPreference <= 3.0) return "dinner";
        if (foodPreference <= 4.5) return "supper";
        if (foodPreference <= 6.0) return "breakfastDinner";
        if (foodPreference <= 7.5) return "breakfastSupper";
        if (foodPreference <= 9.0) return "dinnerSupper";
        return "breakfastDinnerSupper";
    }

    /**
     * Determines the type of trip based on the description provided.
     *
     * @param description the description of the trip
     * @return the type of trip based on the description, ranging from 0.5 to 5.0, or -1.0 if the type is unknown or unspecified
     */
    private double determineTripType(String description) {
        String[][] keywords = {
                {"góra", "szczyt", "trekking", "wspinaczka", "narty", "szlak", "przełęcz", "termalne", "pasmo", "klif", "hala", "alpinizm", "śnieg", "kaskada", "jaskinia", "schronisko", "widok", "trawers", "górski", "peak"},
                {"morze", "plaża", "nurkowanie", "surfing", "kajakarstwo", "windsurfing", "podwodny", "fale", "ryba", "piach", "zatoka", "ocean", "plażowy", "piaszczysty", "kamienisty", "łódź", "żaglówka", "latarnia", "rafa", "molo"},
                {"rejs", "statek", "port", "przystań", "okręt", "kapitan", "kajuta", "żegluga", "rejsowy", "flota", "jacht", "krążownik", "pokład", "kotwica", "marynarz"},
                {"miasto", "metropolia", "zwiedzanie", "architektura", "muzeum", "zabytek", "ratusz", "rynek", "katedra", "ulica", "miejski", "targ", "kamienica", "wieżowiec", "oprowadzanie", "historyczny", "modernizm", "sztuka", "kultura", "park"},
                {"safari", "dzika", "przyroda", "egzotyka", "dżungla", "zwierzę", "przygoda", "jungle trek", "namiot", "dżip", "las", "eksploracja", "rzeka", "sawanna", "obserwacja", "natura", "ekosystem", "biwak", "słoń", "lew", "tropikalny"}
        };

        PolishStemmer stemmer = new PolishStemmer();
        List<String> lemmas = new LinkedList<>();
        String[] tokens = description.toLowerCase().split("\\s+");

        for (String token : tokens) {
            List<WordData> results = stemmer.lookup(token);
            if (!results.isEmpty()) {
                lemmas.add(results.get(0).getStem().toString());
            } else {
                lemmas.add(token);
            }
        }

        for (int i = 0; i < keywords.length; i++) {
            for (String key : keywords[i]) {
                for (String lemma : lemmas) {
                    if (lemma.equalsIgnoreCase(key)) {
                        return switch (i) {
                            case 0 -> 0.5;
                            case 1 -> 2.0;
                            case 2 -> 3.0;
                            case 3 -> 4.0;
                            case 4 -> 5.0;
                            default -> -1.0;
                        };
                    }
                }
            }
        }
        return -1;
    }
}
