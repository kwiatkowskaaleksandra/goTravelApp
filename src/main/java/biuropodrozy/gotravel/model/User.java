package biuropodrozy.gotravel.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type User.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The first name of the user.
     */
    private String firstname;

    /**
     * The last name of the user.
     */
    private String lastname;

    /**
     * The email of the user.
     */
    @Email
    private String email;

    /**
     * The role of the user.
     */
    private String role;

    /**
     * The phone number of the user.
     */
    @Size(max = 9, min = 9)
    private String phoneNumber;

    /**
     * The city of the user.
     */
    private String city;

    /**
     * The street of the user.
     */
    private String street;

    /**
     * The street number of the user.
     */
    private String streetNumber;

    /**
     * The zip code of the user.
     */
    @Size(max = 5, min = 5)
    private String zipCode;

    /**
     * The set of opinions made by the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<Opinion> opinions;

    /**
     * The set of reservations made by the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    /**
     * The set of own offers created by the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<OwnOffer> ownOffers;

    /**
     * Flag indicating if the user is using two-factor authentication.
     */
    private boolean using2FA;

    /**
     * The secret key for two-factor authentication.
     */
    private String secret2FA;

}
