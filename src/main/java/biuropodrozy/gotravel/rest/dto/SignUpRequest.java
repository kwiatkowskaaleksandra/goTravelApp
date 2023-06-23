package biuropodrozy.gotravel.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type sign up request.
 */
@Data
@AllArgsConstructor
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

}
