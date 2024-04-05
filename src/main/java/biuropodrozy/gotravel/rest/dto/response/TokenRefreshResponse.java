package biuropodrozy.gotravel.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response object for token refresh.
 */
@Getter
@Setter
public class TokenRefreshResponse {

    /**
     * The new access token.
     */
    private String accessToken;

    /**
     * The new refresh token.
     */
    private String refreshToken;

    /**
     * The type of token.
     */
    private String tokenType = "Bearer";

    /**
     * Constructs a new TokenRefreshResponse with the given access token and refresh token.
     *
     * @param accessToken  the new access token
     * @param refreshToken the new refresh token
     */
    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
