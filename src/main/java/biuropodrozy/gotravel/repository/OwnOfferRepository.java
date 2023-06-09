package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.OwnOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Own offer repository.
 */
@Repository
public interface OwnOfferRepository extends JpaRepository<OwnOffer, Long> {

    /**
     * Find by id own offer.
     *
     * @param idOwnOffer the id own offer
     * @return the own offer
     */
    OwnOffer findByIdOwnOffer(Long idOwnOffer);

    /**
     * Find top by descending order by id own offer.
     *
     * @return the own offer
     */
    OwnOffer findTopByOrderByIdOwnOfferDesc();

    /**
     * Find by username user.
     *
     * @param username the username
     * @return list of own offers
     */
    List<OwnOffer> findByUser_Username(String username);
}
