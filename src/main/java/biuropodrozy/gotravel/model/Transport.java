package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Transport.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transports")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransport;

    private String nameTransport;

    private double priceTransport;

    @OneToMany(mappedBy = "idTrip")
    private Set<Trip> trips;

}
