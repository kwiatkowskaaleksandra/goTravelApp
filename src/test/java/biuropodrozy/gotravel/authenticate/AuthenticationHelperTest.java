package biuropodrozy.gotravel.authenticate;

import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthenticationHelperTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationHelper authenticationHelper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void validateAuthentication_returnUser_whenAuthenticationIsInvalid() {
        User user = new User();
        user.setUsername("user");
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);


        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userService.validateAndGetUserByUsername("user")).thenReturn(user);

        User result = authenticationHelper.validateAuthentication();

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void validateAuthentication_returnNull_whenAuthenticationIsInvalid() {
        when(securityContext.getAuthentication()).thenReturn(null);

        User result = authenticationHelper.validateAuthentication();

        assertNull(result);
    }

    @Test
    void validateAuthentication_returnNull_whenPrincipalIsNotUserDetailsImpl() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new Object());

        User result = authenticationHelper.validateAuthentication();

        assertNull(result);
    }
}