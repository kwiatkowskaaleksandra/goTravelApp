package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOwnOfferTypeOfRoom;

    @ManyToOne
    @JoinColumn(name = "idOwnOffer")
    private OwnOffer ownOffer;

    @ManyToOne
    @JoinColumn(name = "idTypeOfRoom")
    private TypeOfRoom typeOfRoom;

    private int numberOfRoom;
}
