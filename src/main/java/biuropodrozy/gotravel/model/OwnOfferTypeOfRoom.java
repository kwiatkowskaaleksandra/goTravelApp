package biuropodrozy.gotravel.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Own offer with type of rooms.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ownOfferTypeOfRoom")
public class OwnOfferTypeOfRoom {

    /**
     * The unique identifier for the own offer type of room.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOwnOfferTypeOfRoom;

    /**
     * The own offer associated with the type of room.
     */
    @ManyToOne
    @JoinColumn(name = "idOwnOffer")
    private OwnOffer ownOffer;

    /**
     * The type of room associated with the own offer.
     */
    @ManyToOne
    @JoinColumn(name = "idTypeOfRoom")
    private TypeOfRoom typeOfRoom;

    /**
     * The number of rooms included in the own offer.
     */
    private int numberOfRoom;
}
