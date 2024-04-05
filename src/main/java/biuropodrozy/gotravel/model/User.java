package biuropodrozy.gotravel.model;

import biuropodrozy.gotravel.security.oauth2.OAuth2Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
     * The phone number of the user.
     */
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
    private String zipCode;

    /**
     * The verification code generated during registration.
     */
    @Column(length = 64)
    private String verificationRegisterCode;

    /**
     * The roles associated with the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

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
     * Set of user trip preferences associated with this user.
     */
    @OneToMany(mappedBy = "user")
    private Set<UserTripPreferences> userTripPreferences;

    /**
     * Flag indicating if the user is using two-factor authentication.
     */
    private boolean using2FA;

    /**
     * The secret key for two-factor authentication.
     */
    private String secret2FA;

    /**
     * The activity specifies the activity of the account
     */
    private boolean activity;

    /**
     * The provider of OAuth2 authentication for the user.
     */
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

}
