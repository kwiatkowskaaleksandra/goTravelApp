package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Opinion;

import java.util.List;

public interface OpinionService {

    List<Opinion> getOpinionsByIdTrip(Long idTrip);

    Opinion saveOpinion(Opinion opinion);

    Opinion getOpinionByIdOpinion(int idOpinion);

    void deleteOpinion(Opinion opinion);

    List<Opinion> getOpinionsByIdUser(Long idUser);
}
