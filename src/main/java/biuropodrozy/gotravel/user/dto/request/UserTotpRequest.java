package biuropodrozy.gotravel.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type User totp.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
