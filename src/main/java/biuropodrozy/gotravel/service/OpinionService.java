package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * The interface Opinion service.
 */
public interface OpinionService {

    /**
     * Retrieves the count of opinions and average stars for a specific trip.
     *
     * @param idTrip the unique identifier of the trip
     * @return a map containing the count of opinions and average stars
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
     * Get opinion by id opinion.
     *
     * @param idOpinion the id opinion
     * @return the opinion
     */
    Opinion getOpinionByIdOpinion(int idOpinion);

    /**
     * Deletes an opinion with the specified ID.
     *
     * @param idOpinion the ID of the opinion to delete
     */
    void deleteOpinion(int idOpinion);

    /**
     * Retrieves the count of opinions for a specific trip.
     *
     * @param idTrip the ID of the trip
     * @return the count of opinions for the specified trip
     */
    int countOpinionsByIdTrip(Long idTrip);
}
