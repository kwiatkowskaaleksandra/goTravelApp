package biuropodrozy.gotravel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Trip offers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    /**
     * The unique identifier for the trip.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrip;

    /**
     * The city associated with the trip.
     */
    @ManyToOne
    @JoinColumn(name = "idCity")
    private City tripCity;

    /**
     * The price of the trip.
     */
    private double price;

    /**
     * The transport associated with the trip.
     */
    @ManyToOne
    @JoinColumn(name = "idTransport")
    private Transport tripTransport;

    /**
     * The accommodation associated with the trip.
     */
    @ManyToOne
    @JoinColumn(name = "idAccommodation")
    private Accommodation tripAccommodation;

    /**
     * The type of food provided in the trip.
     */
    private String food;

    @ManyToOne
    @JoinColumn(name = "idTypeOfTrip")
    private TypeOfTrip typeOfTrip;

    /**
     * The number of days of the trip.
     */
    private int numberOfDays;

    /**
     * The set of photos associated with the trip.
     */
    @OneToMany(mappedBy = "trip")
    private Set<Photo> tripPhoto;

    /**
     * The representative photo of the trip.
     */
    private String representativePhoto;

    /**
     * The description of the trip.
     */
    @Column(columnDefinition = "LONGTEXT")
    private String tripDescription;

    /**
     * The set of opinions associated with the trip.
     */
    @OneToMany(mappedBy = "trip")
    private Set<Opinion> tripOpinion;

    /**
     * The set of attractions included in the trip.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "attractions_trips", joinColumns = {@JoinColumn(name = "idTrip")}, inverseJoinColumns = {@JoinColumn(name = "idAttraction")})
    private Set<Attraction> tripAttraction;

    /**
     * The set of reservations made for the trip.
     */
    @OneToMany(mappedBy = "trip")
    private Set<Reservation> reservations;

}
