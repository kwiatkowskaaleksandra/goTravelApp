package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Own offer type of rooms repository.
 */
@Repository
public interface OwnOfferTypeOfRoomRepository extends JpaRepository<OwnOfferTypeOfRoom, Integer> {

    /**
     * Find own offer type of rooms by id own offer.
     *
     * @param id the id own offer
     * @return list of own offer type of rooms
     */
    List<OwnOfferTypeOfRoom> findByOwnOffer_IdOwnOffer(Long id);
}
