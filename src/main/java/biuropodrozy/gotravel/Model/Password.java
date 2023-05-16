package biuropodrozy.gotravel.Model;/*
 * @project gotravel
 * @author kola
 */

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Password {

    @NotBlank
    String oldPassword;
    @NotBlank
    String newPassword;
    @NotBlank
    String newPassword2;
}
