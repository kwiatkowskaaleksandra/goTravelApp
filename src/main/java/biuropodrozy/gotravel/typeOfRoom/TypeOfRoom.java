package biuropodrozy.gotravel.typeOfRoom;

import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type 'Type of room'.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "typesOfRooms")
public class TypeOfRoom {

    /**
     * The unique identifier for the type of room.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypeOfRoom;

    /**
     * The type of the room.
     */
    private String type;

    /**
     * The price of the room.
     */
    private double roomPrice;

    /**
     * The set of reservations associated with the type of room.
     */
    @OneToMany(mappedBy = "typeOfRoom", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ReservationsTypeOfRoom> typeOfRooms;

    /**
     * The set of own offer associated with the type of room.
     */
    @OneToMany(mappedBy = "typeOfRoom", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OwnOfferTypeOfRoom> typeOfRoomsOwnOffer;

}
