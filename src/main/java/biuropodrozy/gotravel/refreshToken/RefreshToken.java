package biuropodrozy.gotravel.refreshToken;

import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Entity representing a refresh token for user authentication.
 */
@Entity(name = "refreshtoken")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {

    /**
     * Represents a refresh token entity used for user authentication.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The user associated with the refresh token.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"opinions", "roles", "reservations", "ownOffer", "userTripPreferences", "favoriteTrips"})
    private User user;

    /**
     * The token value.
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * The expiry date of the token.
     */
    @Column(nullable = false)
    private Instant expiryDate;
}
