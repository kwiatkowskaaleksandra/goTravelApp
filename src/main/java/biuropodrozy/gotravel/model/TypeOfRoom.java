package biuropodrozy.gotravel.model;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Type of room.
 */
@Data
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
    @OneToMany(mappedBy = "typeOfRoom")
    private Set<ReservationsTypeOfRoom> typeOfRooms;
}
