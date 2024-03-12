package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Insurance.
 */
@Data
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
    @OneToMany(mappedBy = "insuranceReservation")
    private Set<Reservation> reservations;

    /**
     * The set of own offers associated with this insurance.
     */
    @OneToMany(mappedBy = "insuranceOwnOffer")
    private Set<OwnOffer> ownOffers;
}
