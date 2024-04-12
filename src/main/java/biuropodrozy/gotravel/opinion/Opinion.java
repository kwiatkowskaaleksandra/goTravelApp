package biuropodrozy.gotravel.opinion;

import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The type Opinion.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "opinions")
public class Opinion {

    /**
     * The unique identifier for the opinion.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOpinion;

    /**
     * The description of the opinion.
     */
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    /**
     * The number of stars given in the opinion.
     */
    private double stars;

    /**
     * The date when the opinion was given.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfAddingTheOpinion;

    /**
     * The user who provided the opinion.
     */
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties({"opinions", "roles", "reservations", "ownOffer", "userTripPreferences", "favoriteTrips"})
    private User user;

    /**
     * The trip associated with the opinion.
     */
    @ManyToOne
    @JoinColumn(name = "idTrip")
    @JsonIgnoreProperties({"tripPhoto", "tripOpinion", "tripAttraction", "reservations", "favoriteTrips"})
    private Trip trip;
}
