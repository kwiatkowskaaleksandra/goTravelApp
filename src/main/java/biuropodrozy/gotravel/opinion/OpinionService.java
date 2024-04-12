package biuropodrozy.gotravel.opinion;

import biuropodrozy.gotravel.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * The interface Opinion service.
 */
public interface OpinionService {

    /**
     * Retrieves the count of opinions and the average star rating for a given trip.
     *
     * @param idTrip the unique identifier of the trip
     * @return a map containing the count of opinions and the average star rating
     */
    Map<String, Object> getCountOpinionsAndStars(Long idTrip);

    /**
     * Retrieves a page of opinions associated with a specific trip.
     *
     * @param idTrip    the unique identifier of the trip
     * @param sortType  the type of sorting to be applied (e.g., "ASC", "DESC")
     * @param pageable  pagination information for retrieving opinions
     * @return a page of opinions associated with the specified trip
     */
    Page<Opinion> getOpinionsByIdTrip(Long idTrip, String sortType, Pageable pageable);

    /**
     * Saves an opinion provided by a user.
     *
     * @param opinion the opinion to save
     * @param user    the user providing the opinion
     */
    void saveOpinion(Opinion opinion, User user);

    /**
     * Retrieves an opinion by its unique identifier.
     *
     * @param idOpinion the unique identifier of the opinion
     * @return the opinion with the specified identifier
     */
    Opinion getOpinionByIdOpinion(int idOpinion);

    /**
     * Deletes an opinion by its unique identifier.
     *
     * @param idOpinion the unique identifier of the opinion to be deleted
     */
    void deleteOpinion(int idOpinion);

    /**
     * Counts the number of opinions associated with a specific trip.
     *
     * @param idTrip the unique identifier of the trip
     * @return the number of opinions associated with the specified trip
     */
    int countOpinionsByIdTrip(Long idTrip);
}
