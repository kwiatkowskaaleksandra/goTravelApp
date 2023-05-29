package biuropodrozy.gotravel.repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.OwnOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnOfferRepository extends JpaRepository<OwnOffer, Long> {

    OwnOffer findByIdOwnOffer(Long idOwnOffer);

    OwnOffer findTopByOrderByIdOwnOfferDesc();

    List<OwnOffer> findByUser_Username(String username);
}
