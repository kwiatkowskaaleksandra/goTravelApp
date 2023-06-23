package biuropodrozy.gotravel.security;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * The UserDetailsService implementation.
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Service for managing user-related operations.
     */
    private final UserService userService;

    /**
     * Get detailed user information based on the given username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Nazwa użytkownika: %s nie została odnaleziona.", username)));
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        return mapUserToCustomUserDetails(user, authorities);
    }

    /**
     * Mapping user to custom user details.
     *
     * @param user the user
     * @param authorities the authorities
     * @return the custom user details
     */
    private CustomUserDetails mapUserToCustomUserDetails(final User user,
                                                         final List<SimpleGrantedAuthority> authorities) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(user.getId());
        customUserDetails.setUsername(user.getUsername());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setFirstname(user.getFirstname());
        customUserDetails.setLastname(user.getLastname());
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }
}
