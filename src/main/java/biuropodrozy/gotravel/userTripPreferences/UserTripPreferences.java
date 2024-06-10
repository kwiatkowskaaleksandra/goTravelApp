package biuropodrozy.gotravel.userTripPreferences;


import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing user trip preferences.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "userTripPreferences")
public class UserTripPreferences {

    /**
     * The unique identifier for user trip preferences.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserTripPreferences;

    /**
     * The activity level preference.
     */
    private double activityLevel;

    /**
     * The price level preference.
     */
    private double priceLevel;

    /**
     * The duration preference.
     */
    private double duration;

    /**
     * The trip type preference.
     */
    private double tripType;

    /**
     * The food preference.
     */
    private double food;

    /**
     * The user associated with these trip preferences.
     */
    @OneToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties({"opinions", "roles", "reservations", "ownOffer", "userTripPreferences", "favoriteTrips"})
    private User user;

}
