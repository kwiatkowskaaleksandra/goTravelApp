package biuropodrozy.gotravel.favoriteTrip;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FavoriteTripsControllerTest {

    private MockMvc mockMvc;
    @Mock private FavoriteTripsService favoriteTripsService;
    @Mock private AuthenticationHelper authenticationHelper;
    @InjectMocks
    private FavoriteTripsController favoriteTripsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favoriteTripsController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void addToFavoritesAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idTrip = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(post("/api/favoriteTrips/addToFavorites")
                        .param("idTrip", idTrip.toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(favoriteTripsService, times(1)).addToFavorites(user, idTrip);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void addToFavoritesUnauthorized() throws Exception {

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/favoriteTrips/addToFavorites")
                        .param("idTrip", "1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(favoriteTripsService, never()).addToFavorites(any(User.class), anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getFavoritesTripsAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Trip trip1 = new Trip();
        trip1.setIdTrip(1L);
        Trip trip2 = new Trip();
        trip2.setIdTrip(2L);

        List<Trip> favoriteTrips = List.of(trip1, trip2);

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(favoriteTripsService.getFavoritesTrips(user)).thenReturn(favoriteTrips);

        mockMvc.perform(get("/api/favoriteTrips/getFavoritesTrips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(favoriteTripsService, times(1)).getFavoritesTrips(user);

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getFavoritesTripsUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/favoriteTrips/getFavoritesTrips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(favoriteTripsService, never()).getFavoritesTrips(any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void removeTripFromFavoritesAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idTrip = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(delete("/api/favoriteTrips/removeTripFromFavorites")
                        .param("idTrip", idTrip.toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(favoriteTripsService, times(1)).removeTripFromFavorites(user, idTrip);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void removeTripFromFavoritesUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(delete("/api/favoriteTrips/removeTripFromFavorites")
                        .param("idTrip", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(favoriteTripsService, never()).removeTripFromFavorites(any(User.class), anyLong());
    }
}