package biuropodrozy.gotravel.ownOffer;

import biuropodrozy.gotravel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Own offer repository.
 */
@Repository
public interface OwnOfferRepository extends JpaRepository<OwnOffer, Long> {

    /**
     * Find by id own offer.
     *
     * @param idOwnOffer the id own offer
     * @return the own offer
     */
    OwnOffer findByIdOwnOffer(Long idOwnOffer);

    /**
     * Retrieves a list of future departures for a specific user.
     *
     * @param user the user for whom to retrieve future departures
     * @return a list of future departures for the user
     */
    @Query("SELECT o FROM OwnOffer o WHERE o.user = :user AND o.departureDate >= CURRENT_DATE")
    List<OwnOffer> findFutureDeparturesForUser(User user);

    /**
     * Retrieves a list of past departures for a specific user.
     *
     * @param user the user for whom to retrieve past departures
     * @return a list of past departures for the user
     */
    @Query("SELECT o FROM OwnOffer o WHERE o.user = :user AND o.departureDate < CURRENT_DATE")
    List<OwnOffer> findPastDeparturesForUser(User user);

    /**
     * Finds all own offers with future departure dates that have not been accepted.
     *
     * @return a list of own offers with future departure dates that have not been accepted
     */
    @Query("SELECT o FROM OwnOffer o WHERE o.accepted = FALSE AND o.changedAcceptanceState = FALSE AND o.departureDate > CURRENT_DATE")
    List<OwnOffer> findAllFutureDeparturesNotAccepted();
}
