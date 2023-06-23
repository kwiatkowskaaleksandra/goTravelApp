package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.mapper.UserMapper;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.model.Password;
import biuropodrozy.gotravel.rest.dto.UserDto;
import biuropodrozy.gotravel.security.CustomUserDetails;
import biuropodrozy.gotravel.security.TotpService;
import biuropodrozy.gotravel.service.UserService;
import biuropodrozy.gotravel.service.ReservationService;
import biuropodrozy.gotravel.service.OwnOfferService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.service.OpinionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static biuropodrozy.gotravel.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

/**
 * The type User controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Service for managing user-related operations.
     */
    private final UserService userService;

    /**
     * Service for managing reservation-related operations.
     */
    private final ReservationService reservationService;

    /**
     * Service for managing own offer-related operations.
     */
    private final OwnOfferService ownOfferService;

    /**
     * Service for managing reservations of type of room-related operations.
     */
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;

    /**
     * Service for managing own offers of type of room-related operations.
     */
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    /**
     * Mapper for mapping User entities to User DTOs.
     */
    private final UserMapper userMapper;

    /**
     * Service for managing opinion-related operations.
     */
    private final OpinionService opinionService;

    /**
     * Password encoder for encoding and decoding passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Length of zip code.
     */
    private static final int ZIP_CODE_LENGTH = 5;

    /**
     * Length of phone number.
     */
    private static final int PHONE_NUMBER_LENGTH = 9;

    /**
     * Minimum length of password.
     */
    private static final int PASSWORD_MIN_LENGTH = 5;

    /**
     * Service for managing Time-based One-Time Password (TOTP) operations.
     */
    @Autowired
    private TotpService totpService;

    /**
     * Get current user response entity.
     *
     * @param currentUser the current user
     * @return the user response entity
     */
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal final CustomUserDetails currentUser) {
        return userService.validateAndGetUserByUsername(currentUser.getUsername());
    }

    /**
     * Get user by username response entity.
     *
     * @param username the username
     * @return the user dto response entity
     */
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable final String username) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
    }

    /**
     * Delete user response entity.
     *
     * @param username the username
     * @return the response entity
     */
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/deleteUser/{username}")
    public UserDto deleteUser(@PathVariable final String username) {
        User user = userService.validateAndGetUserByUsername(username);

        List<Opinion> opinions = opinionService.getOpinionsByIdUser(user.getId());
        opinions.forEach(opinionService::deleteOpinion);

        List<Reservation> reservations = reservationService.getReservationByIdUser(user.getId());
        reservations.forEach((Reservation reservation) -> {
            List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(reservation.getIdReservation());
            reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));
            reservationService.deleteReservation(reservation);
        });

        List<OwnOffer> ownOffers = ownOfferService.getAllOwnOfferByUsername(username);
        ownOffers.forEach((OwnOffer offer) -> {
            List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(offer.getIdOwnOffer());
            ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));
            ownOfferService.deleteOwnOffer(offer);
        });

        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }

    /**
     * Update user info response entity.
     *
     * @param username the username
     * @param user the user
     * @return the response entity
     */
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/update/{username}")
    public String updateUser(@PathVariable final String username, @Valid @RequestBody final User user) {
        User existingUser = userService.validateAndGetUserByUsername(username);
        String token = "";
        if (user.getUsername() == null || user.getLastname() == null || user.getFirstname() == null || user.getEmail() == null || user.getPhoneNumber() == null || user.getCity() == null
                || user.getStreet() == null || user.getStreetNumber() == null || user.getZipCode() == null) {
            throw new UserException("Wszystkie dane powinny zostać uzupełnione.");
        } else if (user.getZipCode().length() != ZIP_CODE_LENGTH) {
            throw new UserException("Kod pocztowy musi zawierać pięć cyfr.");
        } else if (user.getPhoneNumber().length() != PHONE_NUMBER_LENGTH) {
            throw new UserException("Numer telefonu musi zawierać dziewięć cyfr.");
        } else if (!user.getEmail().matches("(.*)@(.*)")) {
            throw new UserException("Błedny adres email.");
        } else {
            existingUser.setUsername(user.getUsername());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setCity(user.getCity());
            existingUser.setStreet(user.getStreet());
            existingUser.setStreetNumber(user.getStreetNumber());
            existingUser.setZipCode(user.getZipCode());
            if (existingUser.isUsing2FA() && !user.isUsing2FA()) {
                existingUser.setUsing2FA(false);
                existingUser.setSecret2FA(null);
            } else if (!existingUser.isUsing2FA() && user.isUsing2FA()) {
                existingUser.setUsing2FA(true);
                String secret = totpService.generateSecret();
                existingUser.setSecret2FA(secret);
                token = totpService.generateQRUrl(existingUser);
            }
            userService.saveUser(existingUser);
            return token;
        }
    }

    /**
     * Update password response entity.
     *
     * @param username the username
     * @param password the password
     * @return the user response entity
     */
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/updatePassword/{username}")
    public User updatePassword(@PathVariable final String username, @Valid @RequestBody final Password password) {
        User existingUser = userService.validateAndGetUserByUsername(username);

        if (!passwordEncoder.matches(password.getOldPassword(), existingUser.getPassword())) {
            throw new UserException("Podane stare hasło jest inne niż aktualne.");
        } else if (!password.getNewPassword().equals(password.getNewPassword2())) {
            throw new UserException("Nowe hasło należy poprawnie wpisać dwukrotnie.");
        } else if (passwordEncoder.matches(password.getNewPassword(), existingUser.getPassword())) {
            throw new UserException("Nowe hasło musi się różnić od poprzedniego.");
        } else if (password.getNewPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new UserException("Hasło powinno mieć więcej niż 5 znaków.");
        } else {
            existingUser.setPassword(passwordEncoder.encode(password.getNewPassword()));
            return userService.saveUser(existingUser);
        }
    }

}
