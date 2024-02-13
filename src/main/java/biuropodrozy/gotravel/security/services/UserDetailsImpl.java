package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The UserDetails implementation.
 */
@Data
public class UserDetailsImpl implements UserDetails {

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
     * The collection of granted authorities for the user.
     */
    private Collection<? extends GrantedAuthority> authorities;

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
}
