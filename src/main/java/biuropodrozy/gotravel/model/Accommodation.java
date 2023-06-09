package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * The type Accommodation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accommodation")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccommodation;

    private String nameAccommodation;

    private double priceAccommodation;

    @OneToMany(mappedBy = "idTrip")
    private Set<Trip> trips;

    @OneToMany(mappedBy = "idOwnOffer")
    private Set<OwnOffer> ownOffers;
}
