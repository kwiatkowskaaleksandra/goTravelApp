package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return UserDetailsImpl.build(user);
    }

}
