package biuropodrozy.gotravel.authenticate.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
     * User role list.
     */
    private List<String> roles;

    /**
     * The type of token.
     */
    private String tokenType = "Bearer";

    /**
     * Constructs a new TokenRefreshResponse with the given access token and refresh token.
     *
     * @param accessToken  the new access token
     * @param refreshToken the new refresh token
     * @param roles the user role list
     */
    public TokenRefreshResponse(String accessToken, String refreshToken, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

}
