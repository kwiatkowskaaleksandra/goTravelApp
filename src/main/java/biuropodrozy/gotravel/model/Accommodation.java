package biuropodrozy.gotravel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;


/**
 * The type Accommodation.
 */
@Data
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
    @OneToMany(mappedBy = "idTrip")
    private Set<Trip> trips;

    /**
     * The set of own offers associated with the accommodation.
     */
    @OneToMany(mappedBy = "idOwnOffer")
    private Set<OwnOffer> ownOffers;
}
