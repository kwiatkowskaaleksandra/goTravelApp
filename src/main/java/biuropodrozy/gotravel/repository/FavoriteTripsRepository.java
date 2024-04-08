package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.FavoriteTrips;
import biuropodrozy.gotravel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A repository interface for managing FavoriteTrips entities in the database.
 */
@Repository
public interface FavoriteTripsRepository extends JpaRepository<FavoriteTrips, Long> {

    /**
     * Retrieves a list of favorite trips associated with the specified user.
     *
     * @param user The user for whom to retrieve the favorite trips.
     * @return A list of favorite trips associated with the specified user.
     */
    List<FavoriteTrips> findAllByUser(User user);

    /**
     * Retrieves the favorite trip for the specified user and trip.
     *
     * @param user The user for whom to retrieve the favorite trip.
     * @param trip_idTrip The id of the trip for which to retrieve the favorite trip.
     * @return The favorite trip for the specified user and trip, or null if not found.
     */
    FavoriteTrips findByUserAndTrip_IdTrip(User user, Long trip_idTrip);
}
