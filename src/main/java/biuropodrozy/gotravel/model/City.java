package biuropodrozy.gotravel.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type City.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    /**
     * The unique identifier for the city.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCity;

    /**
     * The name of the city.
     */
    private String nameCity;

    /**
     * The set of trips associated with the city.
     */
    @OneToMany(mappedBy = "idTrip")
    private Set<Trip> trips;

    /**
     * The set of own offers associated with the city.
     */
    @OneToMany(mappedBy = "idOwnOffer")
    private Set<OwnOffer> ownOffers;

    /**
     * The country to which the city belongs.
     */
    @ManyToOne
    @JoinColumn(name = "idCountry")
    private Country country;
}
