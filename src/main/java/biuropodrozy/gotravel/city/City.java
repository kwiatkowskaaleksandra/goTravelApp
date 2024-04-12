package biuropodrozy.gotravel.city;

import biuropodrozy.gotravel.country.Country;
import biuropodrozy.gotravel.ownOffer.OwnOffer;
import biuropodrozy.gotravel.trip.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type City.
 */
@Getter
@Setter
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
    @OneToMany(mappedBy = "idTrip", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Trip> trips;

    /**
     * The set of own offers associated with the city.
     */
    @OneToMany(mappedBy = "idOwnOffer", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OwnOffer> ownOffers;

    /**
     * The country to which the city belongs.
     */
    @ManyToOne
    @JoinColumn(name = "idCountry")
    @JsonIgnoreProperties({"cities"})
    private Country country;
}
