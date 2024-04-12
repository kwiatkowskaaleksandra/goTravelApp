//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.exception.DuplicatedUserInfoException;
//import biuropodrozy.gotravel.exception.UserSignUpException;
//import biuropodrozy.gotravel.user.User;
//import biuropodrozy.gotravel.user.dto.request.UserTotpRequest;
//import biuropodrozy.gotravel.authenticate.dto.response.AuthResponse;
//import biuropodrozy.gotravel.authenticate.dto.request.LoginRequest;
//import biuropodrozy.gotravel.authenticate.dto.request.SignUpRequest;
//import biuropodrozy.gotravel.security.TokenProvider;
//import biuropodrozy.gotravel.security.services.TotpService;
//import biuropodrozy.gotravel.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.lang.reflect.Field;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AuthControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @Mock
//    private TokenProvider tokenProvider;
//    @Mock
//    private TotpService totpService;
//    @InjectMocks
//    private AuthenticateController authController;
//    private User user;
//    private User user1;
//    private LoginRequest loginRequest;
//    private UserTotpRequest userTotp;
//    private SignUpRequest signUpRequest;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setId(1L);
//        user.setUsername("krysia1234");
//        user.setPassword("krysia1234");
//        user.setEmail("krysia@wp.pl");
//        user.setRole("USER");
//
//        user1 = new User();
//      //  user1.setId(2L);
//        user1.setUsername("krysia12");
//        user1.setPassword("123456");
//        user1.setFirstname("Krysia");
//        user1.setLastname("Nowak");
//        user1.setEmail("krysia12@wp.pl");
//        user1.setRole("USER");
//
//        userTotp = new UserTotpRequest();
//        userTotp.setUsername(user.getUsername());
//        userTotp.setTotp(312098);
//
//        loginRequest = new LoginRequest("krysia1234", "krysia1234");
//    }
//
//    @Test
//    void login() {
//        String token = "generatedtoken";
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
//        when(tokenProvider.generate(authentication)).thenReturn(token);
//        AuthResponse expectedResponse = new AuthResponse(token);
//
//        AuthResponse response = authController.login(loginRequest);
//        assertEquals(expectedResponse, response);
//        verify(tokenProvider).generate(authentication);
//    }
//
//    @Test
//    void findUser() {
//        user.setSecret2FA("secret2FA");
//        when(userService.validateAndGetUserByUsername(loginRequest.getUsername())).thenReturn(user);
//        boolean using2FA = authController.findUser(loginRequest);
//        assertFalse(using2FA);
//    }
//
//    @Test
//    void verifyCode() throws NoSuchFieldException, IllegalAccessException {
//        user.setUsing2FA(true);
//        user.setSecret2FA("secret2FA");
//        Field totpServiceField = AuthenticateController.class.getDeclaredField("totpService");
//        totpServiceField.setAccessible(true);
//        totpServiceField.set(authController, totpService);
//
//        when(userService.validateAndGetUserByUsername(userTotp.getUsername())).thenReturn(user);
//        when(totpService.verifyCode(user.getSecret2FA(), userTotp.getTotp())).thenReturn(true);
//        boolean verify = authController.verifyCode(userTotp);
//        assertTrue(verify);
//    }
//
//    @Test
//    void signUpDuplicatedUsernameInfoException() {
//        signUpRequest = new SignUpRequest("krysia1234", "123456", "Krysia", "Nowak", "krycha12@wp.pl");
//        when(userService.hasUserWithUsername("krysia1234")).thenReturn(true);
//        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> {
//            AuthResponse response = authController.signUp(signUpRequest);
//        });
//        assertEquals("Użytkownik o nazwie: krysia1234 już istnieje.", exception.getMessage());
//    }
//
//    @Test
//    void signUpDuplicatedEmailInfoException() {
//        signUpRequest = new SignUpRequest("krysia12", "123456", "Krysia", "Nowak", "krysia@wp.pl");
//        when(userService.hasUserWithEmail("krysia@wp.pl")).thenReturn(true);
//        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> {
//            AuthResponse response = authController.signUp(signUpRequest);
//        });
//        assertEquals("Użytkownik o adresie email krysia@wp.pl już istnieje.", exception.getMessage());
//    }
//
//    @Test
//    void signUpUserSignUpException() {
//        signUpRequest = new SignUpRequest("krysia12", "123", "Krysia", "Nowak", "krysia12@wp.pl");
//        UserSignUpException exception = assertThrows(UserSignUpException.class, () -> {
//            AuthResponse response = authController.signUp(signUpRequest);
//        });
//        assertEquals("Hasło powinno się składać z co najmniej 5 znaków.", exception.getMessage());
//    }
//
//    @Test
//    void signUpUserSignUpExceptionEmail() {
//        signUpRequest = new SignUpRequest("krysia12", "123456", "Krysia", "Nowak", "krysia12wp.pl");
//        UserSignUpException exception = assertThrows(UserSignUpException.class, () -> {
//            AuthResponse response = authController.signUp(signUpRequest);
//        });
//        assertEquals("Podano zły adres email.", exception.getMessage());
//    }
//
//    @Test
//    void signUp() {
//        String token = "generatedtoken";
//        signUpRequest = new SignUpRequest("krysia12", "123456", "Krysia", "Nowak", "krysia12@wp.pl");
//        when(userService.saveUser(user1)).thenReturn(user1);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
//        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("123456");
//        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
//        when(tokenProvider.generate(authentication)).thenReturn(token);
//        AuthResponse response = new AuthResponse(token);
//
//
//        AuthResponse authResponse = authController.signUp(signUpRequest);
//        assertEquals(response, authResponse);
//        verify(tokenProvider).generate(authentication);
//    }
//
//
//}