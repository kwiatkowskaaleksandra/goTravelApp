package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new UserDetailsServiceImpl(userService);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        String username = "existingUser";
        User user = new User();
        user.setUsername(username);
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertNotNull(result, "UserDetails should not be null");
        assertEquals(username, result.getUsername(), "Usernames should match");
    }

    @Test
    void testLoadUserByUsername_UserNotFound_ThrowsException() {
        String username = "nonExistingUser";
        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username), "Should throw UsernameNotFoundException for non-existing user");
    }
}