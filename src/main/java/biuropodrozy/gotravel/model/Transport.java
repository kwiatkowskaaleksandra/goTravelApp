package biuropodrozy.gotravel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
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

    /**
     * The unique identifier for the transport.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransport;

    /**
     * The name of the transport.
     */
    private String nameTransport;

    /**
     * The price of the transport.
     */
    private double priceTransport;

    /**
     * The set of trips associated with the transport.
     */
    @OneToMany(mappedBy = "idTrip")
    private Set<Trip> trips;

}
