package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Own offer type of rooms repository.
 */
@Repository
public interface OwnOfferTypeOfRoomRepository extends JpaRepository<OwnOfferTypeOfRoom, Integer> {

}
