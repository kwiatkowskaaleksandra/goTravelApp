package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.exception.OpinionException;
import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.OpinionRepository;
import biuropodrozy.gotravel.service.OpinionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Opinion service implementation.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class OpinionServiceImpl implements OpinionService {

    /**
     * The OpinionRepository instance used for accessing and manipulating opinion data.
     */
    private final OpinionRepository opinionRepository;

    /**
     * Retrieves the count of opinions and the average star rating for a given trip.
     *
     * @param idTrip the unique identifier of the trip
     * @return a map containing the count of opinions and the average star rating
     */
    @Override
    public Map<String, Object> getCountOpinionsAndStars(Long idTrip) {
        List<Opinion> opinionList = opinionRepository.findByTrip_IdTrip(idTrip);
        long countNumberOfOpinion = 0;
        double countStars = 0;
        double averageOpinionCalculated = 0;

        for (Opinion opinion: opinionList) {
            countNumberOfOpinion++;
            countStars += opinion.getStars();
        }

        if (countNumberOfOpinion != 0 && countStars != 0) averageOpinionCalculated = Math.round((countStars / countNumberOfOpinion) * 100.0) / 100.0;

        Map<String, Object> result = new HashMap<>();
        result.put("countNumberOfOpinion", countNumberOfOpinion);
        result.put("averageOpinionCalculated", averageOpinionCalculated);

        return result;
    }

    /**
     * Retrieves a page of opinions associated with a specific trip.
     *
     * @param idTrip    the unique identifier of the trip
     * @param sortType  the type of sorting to be applied (e.g., "ASC", "DESC")
     * @param pageable  pagination information for retrieving opinions
     * @return a page of opinions associated with the specified trip
     */
    @Override
    public Page<Opinion> getOpinionsByIdTrip(final Long idTrip, String sortType, Pageable pageable) {
        List<Opinion> opinionList = opinionRepository.findByTrip_IdTrip(idTrip);
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();

        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, opinionList.size());

        if ("ASC".equals(sortType)) opinionList.sort(Comparator.comparing(Opinion::getDateOfAddingTheOpinion));
        else if ("DESC".equals(sortType)) opinionList.sort(Comparator.comparing(Opinion::getDateOfAddingTheOpinion).reversed());

        return new PageImpl<>(opinionList.subList(start, end), pageable, opinionList.size());
    }

    /**
     * Saves a new opinion provided by a user.
     *
     * @param opinion the opinion to be saved
     * @param user    the user submitting the opinion
     */
    @Override
    public void saveOpinion(final Opinion opinion, User user) {
        validateOpinionData(opinion);

        LocalDate dateOfAddingTheOpinion = LocalDate.now();
        opinion.setDateOfAddingTheOpinion(dateOfAddingTheOpinion);
        opinion.setUser(user);
        opinionRepository.save(opinion);
        log.info("The review has been added.");
    }

    /**
     * Retrieves an opinion by its unique identifier.
     *
     * @param idOpinion the unique identifier of the opinion
     * @return the opinion with the specified identifier
     */
    @Override
    public Opinion getOpinionByIdOpinion(final int idOpinion) {
        return opinionRepository.findOpinionByIdOpinion(idOpinion);
    }

    /**
     * Deletes an opinion by its unique identifier.
     *
     * @param idOpinion the unique identifier of the opinion to be deleted
     */
    @Override
    public void deleteOpinion(final int idOpinion) {
        Opinion opinion = getOpinionByIdOpinion(idOpinion);
        opinionRepository.delete(opinion);
        log.info("The opinion has been deleted");
    }

    /**
     * Counts the number of opinions associated with a specific trip.
     *
     * @param idTrip the unique identifier of the trip
     * @return the number of opinions associated with the specified trip
     */
    @Override
    public int countOpinionsByIdTrip(Long idTrip) {
        return opinionRepository.countOpinionByTrip_IdTrip(idTrip);
    }

    /**
     * Validates the data of an opinion before saving.
     *
     * @param opinion the opinion to be validated
     * @throws OpinionException if the opinion data is invalid
     */
    private void validateOpinionData(Opinion opinion) {
        if (opinion.getStars() <= 0) {
            log.error("Please select the number of stars (minimum half a star required).");
            throw new OpinionException("pleaseSelectTheNumberOfStars");
        }
        if (opinion.getDescription().equals("")) {
            log.error("Please complete your opinion.");
            throw new OpinionException("pleaseCompleteYourOpinion");
        }
    }
}
