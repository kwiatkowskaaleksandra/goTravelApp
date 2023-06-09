package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservationsTypeOfRoom;

    @ManyToOne
    @JoinColumn(name = "idReservation")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "idTypeOfRoom")
    private TypeOfRoom typeOfRoom;

    private int numberOfRoom;
}
