package biuropodrozy.gotravel.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The PasswordRequest class represents a request object containing password-related information.
 */
@Getter
@NoArgsConstructor
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

    /**
     * The new password provided in the request.
     */
    @NotBlank
    private String newPassword;
}
