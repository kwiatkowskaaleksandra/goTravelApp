package biuropodrozy.gotravel.insurance;

import biuropodrozy.gotravel.ownOffer.OwnOffer;
import biuropodrozy.gotravel.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type Insurance.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "insurances")
public class Insurance {

    /**
     * The unique identifier for the insurance.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInsurance;

    /**
     * The name of the insurance.
     */
    String name;

    /**
     * The price of the insurance.
     */
    double price;

    /**
     * The set of reservations associated with this insurance.
     */
    @OneToMany(mappedBy = "insuranceReservation", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reservation> reservations;

    /**
     * The set of own offers associated with this insurance.
     */
    @OneToMany(mappedBy = "insuranceOwnOffer", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OwnOffer> ownOffers;
}
