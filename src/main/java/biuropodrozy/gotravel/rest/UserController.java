package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.mapper.UserMapper;
import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.rest.dto.UserDto;
import biuropodrozy.gotravel.security.CustomUserDetails;
import biuropodrozy.gotravel.security.TotpService;
import biuropodrozy.gotravel.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static biuropodrozy.gotravel.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ReservationService reservationService;
    private final OwnOfferService ownOfferService;
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
    private final UserMapper userMapper;
    private final OpinionService opinionService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private TotpService totpService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return userService.validateAndGetUserByUsername(currentUser.getUsername());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/deleteUser/{username}")
    public UserDto deleteUser(@PathVariable String username) {
        User user = userService.validateAndGetUserByUsername(username);

        List<Opinion> opinions = opinionService.getOpinionsByIdUser(user.getId());
        opinions.forEach(opinionService::deleteOpinion);

        List<Reservation> reservations = reservationService.getReservationByIdUser(user.getId());
        reservations.forEach(reservation -> {
            List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(reservation.getIdReservation());
            reservationsTypeOfRooms.forEach((reservationsTypeOfRoomService::deleteReservationsTypeOfRoom));
            reservationService.deleteReservation(reservation);
        });

        List<OwnOffer> ownOffers = ownOfferService.getAllOwnOfferByUsername(username);
        ownOffers.forEach(offer -> {
            List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(offer.getIdOwnOffer());
            ownOfferTypeOfRooms.forEach((ownOfferTypeOfRoomService::deleteOwnOfferTypeOfRoom));
            ownOfferService.deleteOwnOffer(offer);
        });

        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }


    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/update/{username}")
    public String updateUser(@PathVariable String username, @Valid @RequestBody User user) {
        User existingUser = userService.validateAndGetUserByUsername(username);
        String token = "";
        if (user.getZipCode().length() != 5) {
            throw new UserException("Kod pocztowy musi zawierać pięć cyfr.");
        } else if (user.getPhoneNumber().length() != 9) {
            throw new UserException("Numer telefonu musi zawierać dziewięć cyfr.");
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
            if(existingUser.isUsing2FA() && !user.isUsing2FA()){
                existingUser.setUsing2FA(false);
                existingUser.setSecret2FA(null);
            }else if(!existingUser.isUsing2FA() && user.isUsing2FA()){
                existingUser.setUsing2FA(true);
                String secret = totpService.generateSecret();
                existingUser.setSecret2FA(secret);
                token = totpService.generateQRUrl(existingUser);
            }
            userService.saveUser(existingUser);
            return token;
        }
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/updatePassword/{username}")
    public User updatePassword(@PathVariable String username, @Valid @RequestBody Password password) {
        User existingUser = userService.validateAndGetUserByUsername(username);

        if (!passwordEncoder.matches(password.getOldPassword(), existingUser.getPassword())) {
            throw new UserException("Podane stare hasło jest inne niż aktualne.");
        } else if (!password.getNewPassword().equals(password.getNewPassword2())) {
            throw new UserException("Nowe hasło należy poprawnie wpisać dwukrotnie.");
        } else if (passwordEncoder.matches(password.getNewPassword(), existingUser.getPassword())) {
            throw new UserException("Nowe hasło musi się różnić od poprzedniego.");
        } else if (password.getNewPassword().length() < 5) {
            throw new UserException("Hasło powinno mieć więcej niż 5 znaków.");
        } else {
            existingUser.setPassword(passwordEncoder.encode(password.getNewPassword()));
            return userService.saveUser(existingUser);
        }
    }

}
