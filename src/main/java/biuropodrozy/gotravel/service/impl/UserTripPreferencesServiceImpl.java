package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.exception.UserTripPreferencesException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.UserTripPreferences;
import biuropodrozy.gotravel.repository.UserTripPreferencesRepository;
import biuropodrozy.gotravel.service.UserTripPreferencesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing user trip preferences.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserTripPreferencesServiceImpl implements UserTripPreferencesService {

    /**
     * Repository for accessing and managing user trip preferences data in the database.
     */
    private final UserTripPreferencesRepository userTripPreferencesRepository;

    /**
     * Retrieves the trip preferences of the specified user.
     *
     * @param user The user for whom to retrieve trip preferences.
     * @return The trip preferences of the specified user.
     * @throws UserNotFoundException if the user has no preferences added.
     */
    @Override
    public UserTripPreferences getUserTripPreferences(User user) {
        return userTripPreferencesRepository.findByUser(user)
                .orElseThrow(() ->
                        new UserNotFoundException("The user has no preferences added.")
                );
    }

    /**
     * Saves the trip preferences for the specified user.
     *
     * @param user                 The user for whom to save trip preferences.
     * @param userTripPreferences  The trip preferences to save.
     * @return The saved trip preferences.
     * @throws UserTripPreferencesException if the preferences have been entered incorrectly.
     */
    @Override
    public UserTripPreferences savePreferences(User user, UserTripPreferences userTripPreferences) {

        if (userTripPreferences.getActivityLevel() == -1 || userTripPreferences.getDuration() == -1 || userTripPreferences.getPriceLevel() == -1 ||
        userTripPreferences.getTripType() == -1 || userTripPreferences.getFood() == -1) {
            log.error("Preferences have been entered incorrectly.");
            System.out.println( userTripPreferences.getFood());
            throw new UserTripPreferencesException("preferencesHaveBeenEnteredIncorrectly");
        }

        Optional <UserTripPreferences> userTripPrefExist = userTripPreferencesRepository.findByUser(user);

        if (userTripPrefExist.isPresent()) {
            userTripPrefExist.get().setActivityLevel(userTripPreferences.getActivityLevel());
            userTripPrefExist.get().setDuration(userTripPreferences.getDuration());
            userTripPrefExist.get().setPriceLevel(userTripPreferences.getPriceLevel());
            userTripPrefExist.get().setTripType(userTripPreferences.getTripType());
            userTripPrefExist.get().setFood(userTripPreferences.getFood());

            log.info("Preferences have been saved.");
            return userTripPreferencesRepository.save(userTripPrefExist.get());
        } else {
            userTripPreferences.setUser(user);
            log.info("Preferences have been saved.");
            return userTripPreferencesRepository.save(userTripPreferences);
        }
    }
}
