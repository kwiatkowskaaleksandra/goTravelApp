package biuropodrozy.gotravel.reservationTypeOfRoom;

import biuropodrozy.gotravel.reservation.Reservation;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Reservation with type of rooms.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations_type_of_room")
public class ReservationsTypeOfRoom {

    /**
     * The unique identifier for the reservations type of room.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservationsTypeOfRoom;

    /**
     * The reservation associated with the type of room.
     */
    @ManyToOne
    @JoinColumn(name = "idReservation")
    @JsonIgnore
    private Reservation reservation;

    /**
     * The type of room associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idTypeOfRoom")
    @JsonIgnoreProperties({"typeOfRooms", "typeOfRoomsOwnOffer"})
    private TypeOfRoom typeOfRoom;

    /**
     * The number of rooms included in the reservation.
     */
    private int numberOfRoom;
}
