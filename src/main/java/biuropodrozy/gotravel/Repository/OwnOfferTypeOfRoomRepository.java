package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.OwnOfferTypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnOfferTypeOfRoomRepository extends JpaRepository<OwnOfferTypeOfRoom, Integer> {
    List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long id);
}
