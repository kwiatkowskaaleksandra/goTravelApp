package biuropodrozy.gotravel.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type login request.
 */
@Data
@AllArgsConstructor
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
