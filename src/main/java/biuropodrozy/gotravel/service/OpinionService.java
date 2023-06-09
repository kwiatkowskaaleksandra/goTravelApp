package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Opinion;

import java.util.List;

/**
 * The interface Opinion service.
 */
public interface OpinionService {

    /**
     * Get by id trip.
     *
     * @param idTrip the id trip
     * @return list of opinions
     */
    List<Opinion> getOpinionsByIdTrip(Long idTrip);

    /**
     * Save nee opinion.
     *
     * @param opinion the opinion
     * @return the opinion
     */
    Opinion saveOpinion(Opinion opinion);

    /**
     * Get opinion by id opinion.
     *
     * @param idOpinion the id opinion
     * @return the opinion
     */
    Opinion getOpinionByIdOpinion(int idOpinion);

    /**
     * Delete opinion.
     *
     * @param opinion the opinion
     */
    void deleteOpinion(Opinion opinion);

    /**
     * Get opinion by id user.
     *
     * @param idUser the id user
     * @return list of opinions
     */
    List<Opinion> getOpinionsByIdUser(Long idUser);
}
