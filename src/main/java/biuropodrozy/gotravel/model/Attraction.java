package biuropodrozy.gotravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Attraction.
 */

@Data
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
