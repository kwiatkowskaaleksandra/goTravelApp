package biuropodrozy.gotravel.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing user trip preferences.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}
