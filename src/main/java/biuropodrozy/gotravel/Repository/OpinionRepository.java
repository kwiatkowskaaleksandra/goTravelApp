package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

    List<Opinion> findByTrip_IdTrip(Long idTrip);

    Opinion findOpinionByIdOpinion(int idOpinion);

}
