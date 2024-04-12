package biuropodrozy.gotravel.ownOfferTypeOfRoom;

import biuropodrozy.gotravel.ownOffer.OwnOffer;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Own offer with type of rooms.
 */
@Getter
@Setter
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
    @JsonIgnore
    private OwnOffer ownOffer;

    /**
     * The type of room associated with the own offer.
     */
    @ManyToOne
    @JoinColumn(name = "idTypeOfRoom")
    @JsonIgnoreProperties({"typeOfRooms", "typeOfRoomsOwnOffer"})
    private TypeOfRoom typeOfRoom;

    /**
     * The number of rooms included in the own offer.
     */
    private int numberOfRoom;
}
