package biuropodrozy.gotravel.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type Password.
 */
@Data
public class Password {

    @NotBlank
    String oldPassword;
    @NotBlank
    String newPassword;
    @NotBlank
    String newPassword2;
}
