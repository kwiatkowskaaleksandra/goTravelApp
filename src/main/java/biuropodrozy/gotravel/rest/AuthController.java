package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.exception.DuplicatedUserInfoException;
import biuropodrozy.gotravel.exception.UserSignUpException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.model.UserTotp;
import biuropodrozy.gotravel.rest.dto.AuthResponse;
import biuropodrozy.gotravel.rest.dto.LoginRequest;
import biuropodrozy.gotravel.rest.dto.SignUpRequest;
import biuropodrozy.gotravel.security.TokenProvider;
import biuropodrozy.gotravel.security.TotpService;
import biuropodrozy.gotravel.security.WebSecurityConfig;
import biuropodrozy.gotravel.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gotravel")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private static final int PASSWORD_MIN_LENGTH = 5;
    @Autowired
    private TotpService totpService;

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

   @PostMapping("/findUser")
    public boolean findUser(@Valid @RequestBody LoginRequest loginRequest){
        User user = userService.validateAndGetUserByUsername(loginRequest.getUsername());
        return user.isUsing2FA();
    }

    @PostMapping("/verify")
    public boolean verifyCode(@NotEmpty @RequestBody UserTotp userTotp){
        User user = userService.validateAndGetUserByUsername(userTotp.getUsername());
        return totpService.verifyCode(user.getSecret2FA(), userTotp.getTotp());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Użytkownik o nazwie: %s już istnieje.", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Użytkownik o adresie email %s już istnieje.", signUpRequest.getEmail()));
        }
        if (signUpRequest.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new UserSignUpException("Hasło powinno się składać z co najmniej 5 znaków.");
        }
        if(!signUpRequest.getEmail().matches("(.*)@(.*)")){
            throw new UserSignUpException("Podano zły adres email.");
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    public String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    public User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        user.setUsing2FA(false);
        user.setSecret2FA(null);
        System.out.println(user);
        return user;
    }
}
