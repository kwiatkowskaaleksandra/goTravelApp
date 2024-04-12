package biuropodrozy.gotravel.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * The type User totp.
 */
@Getter
@Setter
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
