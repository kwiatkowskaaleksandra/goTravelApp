package biuropodrozy.gotravel.transport;

import biuropodrozy.gotravel.trip.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type Transport.
 */
@Getter
@Setter
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
    @OneToMany(mappedBy = "idTrip", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Trip> trips;

}
