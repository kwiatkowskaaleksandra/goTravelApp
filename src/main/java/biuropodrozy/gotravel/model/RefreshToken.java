package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Entity representing a refresh token for user authentication.
 */
@Entity(name = "refreshtoken")
@Data
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
