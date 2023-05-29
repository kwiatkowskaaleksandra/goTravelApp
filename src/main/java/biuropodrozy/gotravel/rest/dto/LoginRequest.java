package biuropodrozy.gotravel.rest.dto;/*
 * @project gotravel
 * @author kola
 */

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
