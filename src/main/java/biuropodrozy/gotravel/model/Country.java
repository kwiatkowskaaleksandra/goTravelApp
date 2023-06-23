package biuropodrozy.gotravel.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Country.
 */
@Data
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
    @OneToMany(mappedBy = "country")
    private Set<City> cities;
}
