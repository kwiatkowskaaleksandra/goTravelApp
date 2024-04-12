package biuropodrozy.gotravel.attraction;

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
 * The type Attraction.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attractions")
public class Attraction {

    /**
     * The unique identifier for the attraction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAttraction;

    /**
     * The name of the attraction.
     */
    private String nameAttraction;

    /**
     * The price of the attraction.
     */
    private double priceAttraction;

    /**
     * The set of trips associated with the attraction.
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "tripAttraction")
    @JsonIgnore
    private Set<Trip> trips;

    /**
     * The set of own offers associated with the attraction.
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "offerAttraction")
    @JsonIgnore
    private Set<OwnOffer> ownOffers;

}
