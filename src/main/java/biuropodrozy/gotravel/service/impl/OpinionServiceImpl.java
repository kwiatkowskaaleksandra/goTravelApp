package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.repository.OpinionRepository;
import biuropodrozy.gotravel.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Opinion service implementation.
 */
@RequiredArgsConstructor
@Service
public class OpinionServiceImpl implements OpinionService {

    /**
     * The OpinionRepository instance used for accessing and manipulating opinion data.
     */
    private final OpinionRepository opinionRepository;

    /**
     * Get by id trip.
     *
     * @param idTrip the id trip
     * @return list of opinions
     */
    @Override
    public List<Opinion> getOpinionsByIdTrip(final Long idTrip) {
        return opinionRepository.findByTrip_IdTrip(idTrip);
    }

    /**
     * Save nee opinion.
     *
     * @param opinion the opinion
     * @return the opinion
     */
    @Override
    public Opinion saveOpinion(final Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    /**
     * Get opinion by id opinion.
     *
     * @param idOpinion the id opinion
     * @return the opinion
     */
    @Override
    public Opinion getOpinionByIdOpinion(final int idOpinion) {
        return opinionRepository.findOpinionByIdOpinion(idOpinion);
    }

    /**
     * Delete opinion.
     *
     * @param opinion the opinion
     */
    @Override
    public void deleteOpinion(final Opinion opinion) {
        opinionRepository.delete(opinion);
    }

    /**
     * Get opinion by id user.
     *
     * @param idUser the id user
     * @return list of opinions
     */
    @Override
    public List<Opinion> getOpinionsByIdUser(final Long idUser) {
        return opinionRepository.getOpinionByUser_Id(idUser);
    }
}
