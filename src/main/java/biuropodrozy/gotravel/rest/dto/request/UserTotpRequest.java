package biuropodrozy.gotravel.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type User totp.
 */
@Data
public class UserTotpRequest {

    /**
     * The username of the user.
     */
    @NotBlank
    private String username;

    /**
     * The TOTP value provided by the user.
     */
    @NotBlank
    private int totp;
}
