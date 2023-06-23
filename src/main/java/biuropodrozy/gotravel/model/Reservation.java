package biuropodrozy.gotravel.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Temporal;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * The type Reservation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {

    /**
     * The unique identifier for the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    /**
     * The date of the reservation.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfReservation;

    /**
     * The number of children included in the reservation.
     */
    private int numberOfChildren;

    /**
     * The number of adults included in the reservation.
     */
    private int numberOfAdults;

    /**
     * The departure date of the reservation.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate departureDate;

    /**
     *
     */
    private double totalPrice;

    /**
     * The set of room types included in the reservation.
     */
    @OneToMany(mappedBy = "reservation")
    private Set<ReservationsTypeOfRoom> typeOfRoomReservation;

    /**
     * The user who made the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    /**
     * The trip associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idTrip")
    private Trip trip;
}
