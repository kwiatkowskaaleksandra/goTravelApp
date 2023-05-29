package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.repository.OpinionRepository;
import biuropodrozy.gotravel.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpinionServiceImpl implements OpinionService {

    private final OpinionRepository opinionRepository;

    @Override
    public List<Opinion> getOpinionsByIdTrip(Long idTrip) {
        return opinionRepository.findByTrip_IdTrip(idTrip);
    }

    @Override
    public Opinion saveOpinion(Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    @Override
    public Opinion getOpinionByIdOpinion(int idOpinion) {
        return opinionRepository.findOpinionByIdOpinion(idOpinion);
    }

    @Override
    public void deleteOpinion(Opinion opinion) {
        opinionRepository.delete(opinion);
    }

    @Override
    public List<Opinion> getOpinionsByIdUser(Long idUser) {
        return opinionRepository.getOpinionByUser_Id(idUser);
    }
}
