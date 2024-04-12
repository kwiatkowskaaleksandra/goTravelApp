package biuropodrozy.gotravel.trip;

import biuropodrozy.gotravel.accommodation.Accommodation;
import biuropodrozy.gotravel.attraction.Attraction;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.favoriteTrip.FavoriteTrips;
import biuropodrozy.gotravel.opinion.Opinion;
import biuropodrozy.gotravel.photo.Photo;
import biuropodrozy.gotravel.reservation.Reservation;
import biuropodrozy.gotravel.transport.Transport;
import biuropodrozy.gotravel.typeOfTrip.TypeOfTrip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type Trip offers.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
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
    @JsonIgnoreProperties({"trips", "ownOffers"})
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
    @JsonIgnoreProperties({"trips"})
    private Transport tripTransport;

    /**
     * The accommodation associated with the trip.
     */
    @ManyToOne
    @JoinColumn(name = "idAccommodation")
    @JsonIgnoreProperties({"trips", "ownOffers"})
    private Accommodation tripAccommodation;

    /**
     * The type of food provided in the trip.
     */
    private String food;

    /**
     * The type of trip associated with this trip.
     */
    @ManyToOne
    @JoinColumn(name = "idTypeOfTrip")
    @JsonIgnoreProperties({"trips"})
    private TypeOfTrip typeOfTrip;

    /**
     * The number of days of the trip.
     */
    private int numberOfDays;

    /**
     * The set of photos associated with the trip.
     */
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
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
     * The activity level associated with this trip.
     */
    private double activityLevel;

    /**
     * The set of opinions associated with the trip.
     */
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    @JsonIgnore
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
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reservation> reservations;

    /**
     * The set of favorite trips.
     */
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<FavoriteTrips> favoriteTrips;
}
