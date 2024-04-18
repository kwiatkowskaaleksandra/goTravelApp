package biuropodrozy.gotravel.authenticate;

import biuropodrozy.gotravel.authenticate.dto.request.LoginRequest;
import biuropodrozy.gotravel.authenticate.dto.request.SignUpRequest;
import biuropodrozy.gotravel.authenticate.dto.request.TokenRefreshRequest;
import biuropodrozy.gotravel.exception.TokenRefreshException;
import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.refreshToken.RefreshToken;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.role.RoleRepository;
import biuropodrozy.gotravel.security.jwt.JwtUtils;
import biuropodrozy.gotravel.security.services.RefreshTokenService;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import biuropodrozy.gotravel.user.dto.request.PasswordRequest;
import biuropodrozy.gotravel.user.dto.request.UserTotpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AuthenticateControllerTest {

    private MockMvc mockMvc;
    @Mock private UserService userService;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtils jwtUtils;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private RefreshTokenService tokenService;
    @Mock private AuthenticationHelper authenticationHelper;
    @InjectMocks private AuthenticateController authenticateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticateController).build();
    }

    @Test
    void whenValidLogin_thenReturnsJwt() throws Exception {
        LoginRequest loginRequest = new LoginRequest("username", "password");

        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("username@example.com");
        user.setPassword("password");
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(1L);
        refreshToken.setUser(user);
        refreshToken.setToken("refreshToken");
        refreshToken.setExpiryDate(Instant.now().plusSeconds(600));
        String jwtToken = "jwtToken";

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn(jwtToken);
        when(userService.validateAndGetUserByUsername("username")).thenReturn(user);
        when(tokenService.createRefreshToken(user.getId())).thenReturn(refreshToken);

        mockMvc.perform(post("/gotravel/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("jwtToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"))
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    public void testIsUsing2FA_WhenUserHas2FAEnabled_ShouldReturnTrue() throws Exception {
        String username = "user1";
        when(userService.isUsing2FA(username)).thenReturn(true);

        mockMvc.perform(get("/gotravel/isUsing2FA")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testIsUsing2FA_WhenUserHas2FADisabled_ShouldReturnFalse() throws Exception {
        String username = "user1";
        when(userService.isUsing2FA(username)).thenReturn(false);

        mockMvc.perform(get("/gotravel/isUsing2FA")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testVerify2FACode_WhenCodeIsValid_ShouldReturnTrue() throws Exception {
        UserTotpRequest totpRequest = new UserTotpRequest();
        totpRequest.setUsername("user1");
        totpRequest.setTotp(123456);

        when(userService.verify2faCode(any(UserTotpRequest.class))).thenReturn(true);

        mockMvc.perform(post("/gotravel/verify2FACode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(totpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testVerify2FACode_WhenCodeIsInvalid_ShouldReturnFalse() throws Exception {
        UserTotpRequest totpRequest = new UserTotpRequest();
        totpRequest.setUsername("user1");
        totpRequest.setTotp(123456);

        when(userService.verify2faCode(any(UserTotpRequest.class))).thenReturn(false);

        mockMvc.perform(post("/gotravel/verify2FACode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(totpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void signUpUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("user", "password", "password", "User", "User", "user@example.com", Set.of("USER_ROLE"));
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User mappedUser = new User();
        mappedUser.setUsername(signUpRequest.getUsername());
        mappedUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        mappedUser.setFirstname(signUpRequest.getFirstname());
        mappedUser.setLastname(signUpRequest.getLastname());
        mappedUser.setEmail(signUpRequest.getEmail());
        mappedUser.setRoles(Set.of(role));

        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        mockMvc.perform(post("/gotravel/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        verify(userService).passwordChecking(any(PasswordRequest.class));
        verify(userService).saveUser(any(User.class));
    }

    @Test
    void signUpAdmin() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("admin", "admin", "admin", "admin", "admin", "admin@example.com", Set.of("admin"));
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_ADMIN);
        User mappedUser = new User();
        mappedUser.setUsername(signUpRequest.getUsername());
        mappedUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        mappedUser.setFirstname(signUpRequest.getFirstname());
        mappedUser.setLastname(signUpRequest.getLastname());
        mappedUser.setEmail(signUpRequest.getEmail());
        mappedUser.setRoles(Set.of(role));

        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        mockMvc.perform(post("/gotravel/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        verify(userService).passwordChecking(any(PasswordRequest.class));
        verify(userService).saveUser(any(User.class));
    }

    @Test
    void signUpModerator() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("moderator", "moderator", "moderator", "moderator", "moderator", "moderator@example.com", Set.of("mod"));
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User mappedUser = new User();
        mappedUser.setUsername(signUpRequest.getUsername());
        mappedUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        mappedUser.setFirstname(signUpRequest.getFirstname());
        mappedUser.setLastname(signUpRequest.getLastname());
        mappedUser.setEmail(signUpRequest.getEmail());
        mappedUser.setRoles(Set.of(role));

        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        mockMvc.perform(post("/gotravel/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        verify(userService).passwordChecking(any(PasswordRequest.class));
        verify(userService).saveUser(any(User.class));
    }

    @Test
    void verifyRegister_ReturnOk() throws Exception {
        String code = "code";
        when(userService.verifyRegisterLink(code)).thenReturn(true);

        mockMvc.perform(get("/gotravel/verifyRegisterLink")
                .param("code", code))
                .andExpect(status().isOk());
    }

    @Test
    void verifyRegister_ThrowException() throws Exception {
        String code = "code";
        when(userService.verifyRegisterLink(code)).thenReturn(false);

        mockMvc.perform(get("/gotravel/verifyRegisterLink")
                        .param("code", code))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserException))
                .andExpect(result -> assertEquals("Link error.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void refreshToken_ReturnNewToken() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        String refToken = "refreshToken";
        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest(refToken);
        User user = new User();
        user.setUsername("username");
        user.setRoles(Set.of(role));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(600));

        when(tokenService.findByToken(refToken)).thenReturn(Optional.of(refreshToken));
        when(tokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(jwtUtils.generateTokenFromUsername(user.getUsername())).thenReturn("newAccessToken");

        mockMvc.perform(post("/gotravel/refreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tokenRefreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value(refreshToken.getToken()))
                .andExpect(jsonPath("$.roles").isArray());

        verify(jwtUtils).generateTokenFromUsername("username");
    }

    @Test
    void refreshToken_ThrowException() throws Exception {
        String refToken = "invalidRefreshToken";
        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest(refToken);

        when(tokenService.findByToken(refToken)).thenReturn(Optional.empty());

        mockMvc.perform(post("/gotravel/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tokenRefreshRequest)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TokenRefreshException))
                .andExpect(result -> assertEquals("Failed for [invalidRefreshToken]: Refresh token is not in database!",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(tokenService, never()).verifyExpiration(any());
        verify(jwtUtils, never()).generateTokenFromUsername(anyString());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void logoutUser_Successful() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(post("/gotravel/signout")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Log out successful!"));

        verify(tokenService).deleteByUserId(any());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void logoutUser_Unauthorized() throws Exception {

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/gotravel/signout")
                        .with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not authenticated"));

        verify(tokenService, never()).deleteByUserId(any());
    }

    @Test
    void sendingAPasswordResetLink() throws Exception {
        String email = "user@example.com";

        mockMvc.perform(get("/gotravel/forgotPassword")
                .param("email", email))
                .andExpect(status().isOk());

        verify(userService, times(1)).resetPassword(email);
    }

}