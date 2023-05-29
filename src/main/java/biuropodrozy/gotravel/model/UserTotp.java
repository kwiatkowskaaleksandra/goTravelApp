package biuropodrozy.gotravel.model;/*
 * @project gotravel
 * @author kola
 */

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserTotp {
    @NotBlank
    String username;
    @NotBlank
    int totp;
}
