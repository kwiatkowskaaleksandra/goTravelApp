package biuropodrozy.gotravel.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for token refresh.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {

    /**
     * The refresh token.
     */
    @NotBlank
    private String refreshToken;
}
