package biuropodrozy.gotravel.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type User totp.
 */
@Data
public class UserTotp {
    @NotBlank
    String username;
    @NotBlank
    int totp;
}
