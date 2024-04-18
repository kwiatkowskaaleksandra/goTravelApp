package biuropodrozy.gotravel.security.jwt;

import biuropodrozy.gotravel.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private AuthenticationTokenFilter authenticationTokenFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_validToken_setsAuthentication() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.addHeader("Authorization", "Bearer validToken");

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(jwtUtils.validateJwtToken("validToken")).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken("validToken")).thenReturn("username");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(mockUserDetails);

        authenticationTokenFilter.doFilterInternal(request, response, filterChain);

        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        assertEquals(mockUserDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_invalidToken_doesNotSetAuthentication() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.addHeader("Authorization", "Bearer invalidToken");

        when(jwtUtils.validateJwtToken("invalidToken")).thenReturn(false);

        authenticationTokenFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noToken_doesNotSetAuthentication() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        authenticationTokenFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}