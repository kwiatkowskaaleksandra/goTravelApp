package biuropodrozy.gotravel.typeOfTrip;

import biuropodrozy.gotravel.trip.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Entity representing the type of trip.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "typeOfTrip")
public class TypeOfTrip {

    /**
     * The unique identifier for the type of trip.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypeOfTrip;

    /**
     * The name of the type of trip.
     */
    private String name;

    /**
     * The set of trips associated with this type of trip.
     */
    @OneToMany(mappedBy = "typeOfTrip", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Trip> trips;
}
