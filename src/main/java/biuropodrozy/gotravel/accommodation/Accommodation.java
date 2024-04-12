package biuropodrozy.gotravel.accommodation;

import biuropodrozy.gotravel.ownOffer.OwnOffer;
import biuropodrozy.gotravel.trip.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


/**
 * The type Accommodation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accommodation")
public class Accommodation {

    /**
     * The unique identifier for the accommodation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccommodation;

    /**
     * The name of the accommodation.
     */
    private String nameAccommodation;

    /**
     * The price of the accommodation.
     */
    private double priceAccommodation;

    /**
     * The set of trips associated with the accommodation.
     */
    @OneToMany(mappedBy = "idTrip", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Trip> trips;

    /**
     * The set of own offers associated with the accommodation.
     */
    @OneToMany(mappedBy = "idOwnOffer", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OwnOffer> ownOffers;

}
