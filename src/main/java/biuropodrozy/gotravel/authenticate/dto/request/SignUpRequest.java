package biuropodrozy.gotravel.authenticate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * The type sign up request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    /**
     * User's username.
     */
    @NotBlank
    private String username;

    /**
     * User's password.
     */
    @NotBlank
    private String password;

    /**
     * Repeated user password.
     */
    @NotBlank
    private String repeatedPassword;

    /**
     * User's first name.
     */
    @NotBlank
    private String firstname;

    /**
     * User's last name.
     */
    @NotBlank
    private String lastname;

    /**
     * User's email address.
     */
    @Email
    private String email;

    /**
     * Set of roles assigned to the user.
     */
    private Set<String> role;

}
