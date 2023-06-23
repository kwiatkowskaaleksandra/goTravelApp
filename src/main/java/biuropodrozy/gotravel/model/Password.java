package biuropodrozy.gotravel.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type Password.
 */
@Data
public class Password {

    /**
     * The old password.
     */
    @NotBlank
    private String oldPassword;

    /**
     * The new password.
     */
    @NotBlank
    private String newPassword;

    /**
     * The confirmation of the new password.
     */
    @NotBlank
    private String newPassword2;
}
