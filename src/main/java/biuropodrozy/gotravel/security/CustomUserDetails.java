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

    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
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
