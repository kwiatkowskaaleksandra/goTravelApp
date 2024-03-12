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
     * Retrieves a list of OwnOfferTypeOfRoom entities associated with a specific OwnOffer.
     *
     * @param idOwnOffer the unique identifier of the OwnOffer
     * @return a list of OwnOfferTypeOfRoom entities associated with the specified OwnOffer
     */
    List<OwnOfferTypeOfRoom> findOwnOfferTypeOfRoomByOwnOffer_IdOwnOffer(Long idOwnOffer);
}
