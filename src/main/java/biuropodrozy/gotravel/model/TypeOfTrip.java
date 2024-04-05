package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Entity representing the type of trip.
 */
@Data
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
    @OneToMany(mappedBy = "typeOfTrip")
    private Set<Trip> trips;
}
