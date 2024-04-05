package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.UserTripPreferences;
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
