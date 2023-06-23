package biuropodrozy.gotravel.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * The UserDetails implementation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

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
    private String password;

    /**
     * The password of the user.
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
     * The collection of granted authorities for the user.
     */
    private Collection<? extends GrantedAuthority> authorities;

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
}
