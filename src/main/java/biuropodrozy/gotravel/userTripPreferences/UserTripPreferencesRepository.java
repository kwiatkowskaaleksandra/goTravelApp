package biuropodrozy.gotravel.userTripPreferences;

import biuropodrozy.gotravel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing user trip preferences.
 */
public interface UserTripPreferencesRepository extends JpaRepository<UserTripPreferences, Long> {

    /**
     * Finds user trip preferences by user.
     *
     * @param user the user whose trip preferences to find
     * @return the user trip preferences, if found
     */
    Optional<UserTripPreferences> findByUser(User user);

}
