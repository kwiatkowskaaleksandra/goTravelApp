package biuropodrozy.gotravel.repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

    List<Opinion> findByTrip_IdTrip(Long idTrip);

    Opinion findOpinionByIdOpinion(int idOpinion);

    List<Opinion> getOpinionByUser_Id(Long idUser);

}
