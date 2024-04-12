package biuropodrozy.gotravel.userTripPreferences;

import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.exception.UserTripPreferencesException;
import biuropodrozy.gotravel.user.User;

/**
 * Service interface for managing user trip preferences.
 */
public interface UserTripPreferencesService {

    /**
     * Retrieves the trip preferences of the specified user.
     *
     * @param user The user for whom to retrieve trip preferences.
     * @return The trip preferences of the specified user.
     * @throws UserNotFoundException if the user has no preferences added.
     */
    UserTripPreferences getUserTripPreferences(User user);

    /**
     * Saves the trip preferences for the specified user.
     *
     * @param user                 The user for whom to save trip preferences.
     * @param userTripPreferences  The trip preferences to save.
     * @return The saved trip preferences.
     * @throws UserTripPreferencesException if the preferences have been entered incorrectly.
     */
    UserTripPreferences savePreferences(User user, UserTripPreferences userTripPreferences);
}
