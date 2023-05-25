package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Exception.DuplicatedUserInfoException;
import biuropodrozy.gotravel.Exception.UserSignUpException;
import biuropodrozy.gotravel.Model.User;
import biuropodrozy.gotravel.Rest.dto.AuthResponse;
import biuropodrozy.gotravel.Rest.dto.LoginRequest;
import biuropodrozy.gotravel.Rest.dto.SignUpRequest;
import biuropodrozy.gotravel.Security.TokenProvider;
import biuropodrozy.gotravel.Security.WebSecurityConfig;
import biuropodrozy.gotravel.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gotravel")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
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
        if (signUpRequest.getPassword().length() < 5) {
            throw new UserSignUpException("Hasło powinno się składać z co najmniej 5 znaków.");
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        return user;
    }
}
