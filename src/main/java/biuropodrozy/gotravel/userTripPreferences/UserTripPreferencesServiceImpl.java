package biuropodrozy.gotravel.userTripPreferences;

import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.exception.UserTripPreferencesException;
import biuropodrozy.gotravel.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of the {@link UserTripPreferencesService} interface for managing user trip preferences.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserTripPreferencesServiceImpl implements UserTripPreferencesService {

    /**
     * Repository for accessing and managing user trip preferences data in the database.
     */
    private final UserTripPreferencesRepository userTripPreferencesRepository;

    @Override
    public UserTripPreferences getUserTripPreferences(User user) {
        return userTripPreferencesRepository.findByUser(user)
                .orElseThrow(() ->
                        new UserNotFoundException("The user has no preferences added.")
                );
    }

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
