package biuropodrozy.gotravel.authenticate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type login request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * The username provided in the login request.
     */
    @NotBlank
    private String username;

    /**
     * The password provided in the login request.
     */
    @NotBlank
    private String password;

}
