package biuropodrozy.gotravel.reservation;

import biuropodrozy.gotravel.insurance.Insurance;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * The type Reservation.
 */
@Getter
@Setter
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
     * Total price of the trip.
     */
    private double totalPrice;

    /**
     * Represents the payment status.
     */
    private boolean payment;

    /**
     * Status of acceptance of the reservation by the employee.
     */
    private boolean accepted;

    /**
     * Acceptance status change status.
     */
    private boolean changedAcceptanceState;

    /**
     * The set of room types included in the reservation.
     */
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"reservation"})
    private Set<ReservationsTypeOfRoom> typeOfRoomReservation;

    /**
     * The user who made the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties({"opinions", "roles", "reservations", "ownOffer", "userTripPreferences", "favoriteTrips"})
    private User user;

    /**
     * The trip associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idTrip")
    @JsonIgnoreProperties({"tripPhoto", "tripOpinion", "tripAttraction", "reservations", "favoriteTrips"})
    private Trip trip;

    /**
     * The insurance associated with this reservation.
     */
    @ManyToOne
    @JoinColumn(name = "idInsurance")
    @JsonIgnoreProperties({"ownOffers", "reservations"})
    private Insurance insuranceReservation;
}
