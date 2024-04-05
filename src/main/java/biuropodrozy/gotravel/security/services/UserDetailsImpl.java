package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.security.oauth2.OAuth2Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The UserDetails implementation.
 */
@Data
@NoArgsConstructor
public class UserDetailsImpl implements OAuth2User, UserDetails {

    /**
     * The unique identifier for the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    @JsonIgnore
    private String password;

    /**
     * The firstname of the user.
     */
    private String firstname;

    /**
     * The last name of the user.
     */
    private String lastname;

    /**
     * The last name of the user.
     */
    private String email;

    /**
     * The account activity
     */
    private boolean activity;

    /**
     * The OAuth2Provider associated with the user.
     */
    private OAuth2Provider provider;

    /**
     * The collection of granted authorities for the user.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Additional attributes associated with the user.
     */
    private Map<String, Object> attributes;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Check if the account has no expired.
     *
     * @return true or false
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account has no locked.
     *
     * @return true or false
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * Check if the credentials' no expired.
     *
     * @return true or false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Check if is enabled.
     *
     * @return true or false
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Constructs a UserDetailsImpl object from the given User object.
     *
     * @param user the User object
     * @return the UserDetailsImpl object constructed from the User object
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    /**
     * Get the additional attributes associated with the user.
     *
     * @return A map of additional attributes.
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Get the name associated with the user.
     *
     * @return The user's name.
     */
    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
