package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.UserTripPreferences;

/**
 * Service interface for managing user trip preferences.
 */
public interface UserTripPreferencesService {

    /**
     * Retrieves the trip preferences of a specific user.
     *
     * @param user The user whose trip preferences are to be retrieved.
     * @return The trip preferences of the specified user.
     */
    UserTripPreferences getUserTripPreferences(User user);

    /**
     * Saves or updates the trip preferences for a specific user.
     *
     * @param user                The user for whom trip preferences are to be saved or updated.
     * @param userTripPreferences The trip preferences to be saved or updated.
     * @return The saved or updated user trip preferences.
     */
    UserTripPreferences savePreferences(User user, UserTripPreferences userTripPreferences);
}
