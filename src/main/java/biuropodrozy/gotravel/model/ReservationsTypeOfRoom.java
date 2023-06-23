package biuropodrozy.gotravel.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Reservation with type of rooms.
 */
@Data
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
    private Reservation reservation;

    /**
     * The type of room associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idTypeOfRoom")
    private TypeOfRoom typeOfRoom;

    /**
     * The number of rooms included in the reservation.
     */
    private int numberOfRoom;
}
