package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Opinion repository.
 */
@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

    /**
     * Find by id trip.
     *
     * @param idTrip the id trip
     * @return list of opinions
     */
    List<Opinion> findByTrip_IdTrip(Long idTrip);

    /**
     * Find opinion by id opinion.
     *
     * @param idOpinion the id opinion
     * @return the opinion
     */
    Opinion findOpinionByIdOpinion(int idOpinion);

    /**
     * Retrieves the count of opinions associated with a specific trip.
     *
     * @param trip_idTrip the ID of the trip
     * @return the count of opinions associated with the trip
     */
    int countOpinionByTrip_IdTrip(Long trip_idTrip);
}
