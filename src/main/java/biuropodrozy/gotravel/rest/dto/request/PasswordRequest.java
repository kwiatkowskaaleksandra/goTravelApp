package biuropodrozy.gotravel.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The PasswordRequest class represents a request object containing password-related information.
 */
@Data
@AllArgsConstructor
public class PasswordRequest {

    /**
     * The password provided in the request.
     */
    private String password;

    /**
     * The repeated password provided in the request for confirmation.
     */
    @NotBlank
    private String repeatedPassword;

    @NotBlank
    private String newPassword;
}
