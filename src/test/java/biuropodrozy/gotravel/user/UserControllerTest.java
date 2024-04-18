package biuropodrozy.gotravel.user;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.user.dto.request.PasswordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AuthenticationHelper authenticationHelper;
    @Mock private UserService userService;
    @Mock private RefreshTokenService refreshTokenService;
    @InjectMocks private UserController userController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getCurrentUserAuthorized() {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setId(1L);
        user.setRoles(Set.of(role));
        user.setUsername("user");
        user.setEmail("username@example.com");
        user.setPassword("password");

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(userService.validateAndGetUserByUsername("user")).thenReturn(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        User result = userController.getCurrentUser(userDetails);
        assertNotNull(result, "User should not be null");
        assertEquals("user", result.getUsername(), "Username should match");

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getCurrentUserUnauthorized() {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setId(1L);
        user.setRoles(Set.of(role));
        user.setUsername("user");
        user.setEmail("username@example.com");
        user.setPassword("password");

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(userService.validateAndGetUserByUsername("user")).thenReturn(null);
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        User result = userController.getCurrentUser(userDetails);
        assertNull(result, "User should not be null");
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteUserAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setRoles(Set.of(role));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(put("/api/users/deleteUser")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("The account has been deactivated."));

        verify(authenticationHelper).validateAuthentication();
        verify(userService, times(1)).deleteUser(user);
        verify(refreshTokenService, times(1)).deleteByUserId(1L);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteUserUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/users/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(userService, never()).deleteUser(any(User.class));
        verify(refreshTokenService, never()).deleteByUserId(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updateUserAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");
        updatedUser.setEmail("updated@example.com");

        when(userService.updateUserData(eq(user), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/updateUserData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()));

        verify(authenticationHelper).validateAuthentication();
        verify(userService, times(1)).updateUserData(any(User.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updateUserUnauthorized() throws Exception {
        User user = new User();
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/users/updateUserData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(userService, never()).updateUserData(any(User.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updatePasswordAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        PasswordRequest passwordRequest = new PasswordRequest("Password123.","Password1234.", "Password1234.");

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(put("/api/users/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Password has been changed."));

        verify(authenticationHelper).validateAuthentication();
        verify(userService, times(1)).changePassword(any(User.class), any(PasswordRequest.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updatePasswordUnauthorized() throws Exception {
        PasswordRequest passwordRequest = new PasswordRequest("Password123.","Password1234.", "Password1234.");
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/users/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(userService, never()).changePassword(any(User.class), any(PasswordRequest.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void changeOf2FAInclusionAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        boolean twoFactorAuthenticationEnable = false;
        String token = "token";

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(userService.changeOf2FAInclusion(any(User.class), eq(twoFactorAuthenticationEnable))).thenReturn(token);

        mockMvc.perform(put("/api/users/changeOf2FAInclusion")
                .param("twoFactorAuthenticationEnable", String.valueOf(twoFactorAuthenticationEnable))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(token));

        verify(authenticationHelper).validateAuthentication();
        verify(userService, times(1)).changeOf2FAInclusion(user, twoFactorAuthenticationEnable);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void changeOf2FAInclusionUnauthorized() throws Exception {
        boolean twoFactorAuthenticationEnable = false;
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/users/changeOf2FAInclusion")
                        .param("twoFactorAuthenticationEnable", String.valueOf(twoFactorAuthenticationEnable))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(userService, never()).changeOf2FAInclusion(any(User.class), anyBoolean());
    }

    @Test
    void resetPasswordAuthorized() throws Exception {
        String email = "john@example.pl";
        PasswordRequest passwordRequest = new PasswordRequest("Password123.","Password1234.", "Password1234.");

        mockMvc.perform(put("/api/users/resetPassword")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isOk());

        verify(userService, times(1)).changePasswordFromResetLink(any(PasswordRequest.class), anyString());
    }

}