package biuropodrozy.gotravel.country;

import biuropodrozy.gotravel.city.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type Country.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    /**
     * The unique identifier for the country.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCountry;

    /**
     * The name of the country.
     */
    private String nameCountry;

    /**
     * The set of cities associated with the country.
     */
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<City> cities;
}
