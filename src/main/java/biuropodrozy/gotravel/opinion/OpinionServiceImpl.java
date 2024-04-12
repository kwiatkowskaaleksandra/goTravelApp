package biuropodrozy.gotravel.opinion;

import biuropodrozy.gotravel.exception.OpinionException;
import biuropodrozy.gotravel.user.User;
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
 * Implementation of the {@link OpinionService} interface.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class OpinionServiceImpl implements OpinionService {

    /**
     * The OpinionRepository instance used for accessing and manipulating opinion data.
     */
    private final OpinionRepository opinionRepository;

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

    @Override
    public void saveOpinion(final Opinion opinion, User user) {
        validateOpinionData(opinion);

        LocalDate dateOfAddingTheOpinion = LocalDate.now();
        opinion.setDateOfAddingTheOpinion(dateOfAddingTheOpinion);
        opinion.setUser(user);
        opinionRepository.save(opinion);
        log.info("The review has been added.");
    }

    @Override
    public Opinion getOpinionByIdOpinion(final int idOpinion) {
        return opinionRepository.findOpinionByIdOpinion(idOpinion);
    }

    @Override
    public void deleteOpinion(final int idOpinion) {
        Opinion opinion = getOpinionByIdOpinion(idOpinion);
        opinionRepository.delete(opinion);
        log.info("The opinion has been deleted");
    }

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
