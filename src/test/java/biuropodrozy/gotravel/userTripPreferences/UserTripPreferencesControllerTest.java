package biuropodrozy.gotravel.userTripPreferences;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserTripPreferencesControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AuthenticationHelper authenticationHelper;
    @Mock UserTripPreferencesService userTripPreferencesService;
    @InjectMocks UserTripPreferencesController userTripPreferencesController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTripPreferencesController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getPreferencesAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        UserTripPreferences userTripPreferences  = new UserTripPreferences(1L, 1, 1, 1, 2, 1, user);

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(userTripPreferencesService.getUserTripPreferences(user)).thenReturn(userTripPreferences);

        mockMvc.perform(get("/api/userTripPreferences/getPreferences")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userTripPreferences)));

        verify(authenticationHelper).validateAuthentication();
        verify(userTripPreferencesService, times(1)).getUserTripPreferences(any(User.class));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getPreferencesUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/userTripPreferences/getPreferences")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(userTripPreferencesService, never()).getUserTripPreferences(any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void savePreferencesAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        UserTripPreferences userTripPreferences  = new UserTripPreferences(1L, 1, 1, 1, 2, 1, user);

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(userTripPreferencesService.savePreferences(user, userTripPreferences)).thenReturn(userTripPreferences);

        mockMvc.perform(post("/api/userTripPreferences/savePreferences")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userTripPreferences)))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(userTripPreferencesService, times(1)).savePreferences(any(User.class), any(UserTripPreferences.class));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void savePreferencesUnauthorized() throws Exception {
        UserTripPreferences userTripPreferences = new UserTripPreferences();

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/userTripPreferences/savePreferences")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userTripPreferences)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(userTripPreferencesService, never()).savePreferences(any(User.class), any(UserTripPreferences.class));
    }
}