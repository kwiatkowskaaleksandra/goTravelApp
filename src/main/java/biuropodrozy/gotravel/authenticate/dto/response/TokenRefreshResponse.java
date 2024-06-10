package biuropodrozy.gotravel.authenticate.dto.response;

import lombok.Getter;

import java.util.List;

/**
 * Response object for token refresh.
 */
@Getter
public class TokenRefreshResponse {

    /**
     * The new access token.
     */
    private final String accessToken;

    /**
     * The new refresh token.
     */
    private final String refreshToken;

    /**
     * User role list.
     */
    private final List<String> roles;

    /**
     * The type of token.
     */
    private final String tokenType = "Bearer";

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
