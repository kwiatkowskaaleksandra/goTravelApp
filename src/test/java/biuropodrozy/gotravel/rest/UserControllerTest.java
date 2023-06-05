package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.mapper.UserMapper;
import biuropodrozy.gotravel.mapper.UserMapperImpl;
import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.rest.dto.UserDto;
import biuropodrozy.gotravel.security.CustomUserDetails;
import biuropodrozy.gotravel.security.TotpService;
import biuropodrozy.gotravel.security.UserDetailsServiceImpl;
import biuropodrozy.gotravel.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TotpService totpService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ReservationService reservationService;
    @Mock
    private ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    @Mock
    private OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
    @Mock
    private OwnOfferService ownOfferService;
    @Mock
    private OpinionService opinionService;
    @InjectMocks
    private UserController userController;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @InjectMocks
    private UserMapperImpl userMapperImpl;
    private User user;
    private UserDetails userDetails;
    private CustomUserDetails customUserDetails;
    private UserDto userDto;
    private Opinion opinion;
    private Reservation reservation;
    private OwnOffer ownOffer;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(1L, "krysia1234", null, null, "krysia@wp.pl", "USER",
                null, null, null, null, null);

        customUserDetails = new CustomUserDetails();
        customUserDetails.setId(1L);
        customUserDetails.setEmail("krysia@wp.pl");
        customUserDetails.setFirstname("Krysia");
        customUserDetails.setLastname("Nowak");
        customUserDetails.setPassword("krysia1234");
        customUserDetails.setUsername("krysia1234");

        user = new User();
        user.setId(1L);
        user.setUsername("krysia1234");
        user.setPassword("krysia1234");
        user.setEmail("krysia@wp.pl");
        user.setRole("USER");
        user.setUsing2FA(true);

        opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setUser(user);

        reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setUser(user);

        ownOffer = new OwnOffer();
        ownOffer.setIdOwnOffer(1L);
        ownOffer.setUser(user);
    }

    @Test
    void getCurrentUser() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(userService.getUserByUsername("krysia1234")).thenReturn(Optional.ofNullable(user));
        UserDetails result = userDetailsService.loadUserByUsername("krysia1234");
        User userResponse = userController.getCurrentUser(customUserDetails);
        assertEquals("USER", result.getAuthorities().iterator().next().getAuthority());
        assertEquals(user, userResponse);
    }

    @Test
    void getUser() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto userDtoReq = userMapperImpl.toUserDto(user);
        UserDto userDto1 = userController.getUser("krysia1234");
        assertThat(userDto1).isNotNull();
        assertEquals(userDtoReq, userDto1);
    }

    @Test
    void getUserWithUserNull() {
        user = null;
        UserDto userDtoReq = userMapperImpl.toUserDto(user);
        assertThat(userDtoReq).isNull();
    }

    @Test
    void deleteUser() {
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        willDoNothing().given(userService).deleteUser(argumentCaptor.capture());
        when(opinionService.getOpinionsByIdUser(1L)).thenReturn(List.of(opinion));
        when(reservationService.getReservationByIdUser(1L)).thenReturn(List.of(reservation));
        when(ownOfferService.getAllOwnOfferByUsername("krysia1234")).thenReturn(List.of(ownOffer));
        UserDto userDto1 = userController.deleteUser("krysia1234");
        assertThat(userDto1).isNull();
    }

    @Test
    void updateUserBlank() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        UserException exception = assertThrows(UserException.class, () -> {
            String update = userController.updateUser("krysia1234", user);
        });
        assertEquals("Wszystkie dane powinny zostać uzupełnione.", exception.getMessage());
    }

    @Test
    void updateUserWithBadZipCode() {
        user.setZipCode("123");
        user.setLastname("Nowak");
        user.setFirstname("Krysia");
        user.setPhoneNumber("123456789");
        user.setEmail("krysiaczek@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setStreetNumber("12b");
        user.setUsing2FA(true);
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        UserException exception = assertThrows(UserException.class, () -> {
            String update = userController.updateUser("krysia1234", user);
        });
        assertEquals("Kod pocztowy musi zawierać pięć cyfr.", exception.getMessage());
    }

    @Test
    void updateUserWithBadPhoneNumber() {
        user.setZipCode("12345");
        user.setLastname("Nowak");
        user.setFirstname("Krysia");
        user.setPhoneNumber("1234567");
        user.setEmail("krysiaczek@wp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setStreetNumber("12b");
        user.setUsing2FA(true);
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        UserException exception = assertThrows(UserException.class, () -> {
            String update = userController.updateUser("krysia1234", user);
        });
        assertEquals("Numer telefonu musi zawierać dziewięć cyfr.", exception.getMessage());
    }

    @Test
    void updateUserWithBadEmail() {
        user.setZipCode("12345");
        user.setLastname("Nowak");
        user.setFirstname("Krysia");
        user.setPhoneNumber("123456789");
        user.setEmail("krysiaczekwp.pl");
        user.setCity("Kielce");
        user.setStreet("Wesoła");
        user.setStreetNumber("12b");
        user.setUsing2FA(true);
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        UserException exception = assertThrows(UserException.class, () -> {
            String update = userController.updateUser("krysia1234", user);
        });
        assertEquals("Błedny adres email.", exception.getMessage());
    }

    @Test
    void updateUserNotUsing2FA() {
        User user1 = new User();
        user1.setZipCode("12345");
        user1.setLastname("Nowak");
        user1.setFirstname("Krysia");
        user1.setPhoneNumber("123456789");
        user1.setEmail("krysiaczek@wp.pl");
        user1.setCity("Kielce");
        user1.setStreet("Wesoła");
        user1.setStreetNumber("12b");
        user1.setUsing2FA(false);
        user1.setEmail("krysia@wp.pl");
        user1.setRole("USER");
        user1.setUsername("krysia1234");

        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        String update = userController.updateUser("krysia1234", user1);
        assertThat(update).isNotNull();
    }

    @Test
    void updateUserUsing2FA() throws IllegalAccessException, NoSuchFieldException {
        user.setUsing2FA(false);
        User user1 = new User();
        user1.setZipCode("12345");
        user1.setLastname("Nowak");
        user1.setFirstname("Krysia");
        user1.setPhoneNumber("123456789");
        user1.setEmail("krysiaczek@wp.pl");
        user1.setCity("Kielce");
        user1.setStreet("Wesoła");
        user1.setStreetNumber("12b");
        user1.setUsing2FA(true);
        user1.setEmail("krysia@wp.pl");
        user1.setRole("USER");
        user1.setUsername("krysia1234");

        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);

        TotpService totpService = Mockito.mock(TotpService.class);
        when(totpService.generateSecret()).thenReturn("mockedSecret");
        when(totpService.generateQRUrl(Mockito.any(User.class))).thenReturn("mockedUrl");

        Field totpServiceField = UserController.class.getDeclaredField("totpService");
        totpServiceField.setAccessible(true);
        totpServiceField.set(userController, totpService);

        String qrUrl = totpService.generateQRUrl(user);
        String update = userController.updateUser("krysia1234", user1);
        assertThat(update).isNotNull();
        System.out.println(qrUrl);
        assertThat(qrUrl).isNotNull();
    }

    @Test
    void updatePasswordWithTheSameFields() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        Password password = new Password();
        password.setNewPassword("krysia1234");
        password.setNewPassword2("krysia1234");
        password.setOldPassword("krysia");
        UserException exception = assertThrows(UserException.class, () -> {
            User update = userController.updatePassword("krysia1234", password);
        });
        assertEquals("Podane stare hasło jest inne niż aktualne.", exception.getMessage());
    }

    @Test
    void updatePasswordWithNewPassAsOldPass() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        Password password = new Password();
        password.setNewPassword("krysia1234");
        password.setNewPassword2("krysia1234");
        password.setOldPassword("krysia1234");
        when(passwordEncoder.matches(password.getOldPassword(), user.getPassword())).thenReturn(true);
        UserException exception = assertThrows(UserException.class, () -> {
            User update = userController.updatePassword("krysia1234", password);
        });
        assertEquals("Nowe hasło musi się różnić od poprzedniego.", exception.getMessage());
    }

    @Test
    void updatePasswordWithBadNewPassTwice() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        Password password = new Password();
        password.setNewPassword("krysia1234");
        password.setNewPassword2("krysia1");
        password.setOldPassword("krysia1234");
        when(passwordEncoder.matches(password.getOldPassword(), user.getPassword())).thenReturn(true);
        UserException exception = assertThrows(UserException.class, () -> {
            User update = userController.updatePassword("krysia1234", password);
        });
        assertEquals("Nowe hasło należy poprawnie wpisać dwukrotnie.", exception.getMessage());
    }

    @Test
    void updatePasswordWithBadNewPass() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        Password password = new Password();
        password.setNewPassword("kry1");
        password.setNewPassword2("kry1");
        password.setOldPassword("krysia1234");
        when(passwordEncoder.matches(password.getOldPassword(), user.getPassword())).thenReturn(true);
        UserException exception = assertThrows(UserException.class, () -> {
            User update = userController.updatePassword("krysia1234", password);
        });
        assertEquals("Hasło powinno mieć więcej niż 5 znaków.", exception.getMessage());
    }

    @Test
    void updatePasswordWithGoodPass() {
        when(userService.validateAndGetUserByUsername("krysia1234")).thenReturn(user);
        Password password = new Password();
        password.setNewPassword("krysiaczek");
        password.setNewPassword2("krysiaczek");
        password.setOldPassword("krysia1234");
        when(passwordEncoder.matches(password.getOldPassword(), user.getPassword())).thenReturn(true);
        when(userService.saveUser(user)).thenReturn(user);
        User update = userController.updatePassword("krysia1234", password);
        assertEquals(user, update);

    }
}