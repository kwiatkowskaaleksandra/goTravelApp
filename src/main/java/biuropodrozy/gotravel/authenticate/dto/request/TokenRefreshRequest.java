package biuropodrozy.gotravel.authenticate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request object for token refresh.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {

    /**
     * The refresh token.
     */
    @NotBlank
    private String refreshToken;
}
