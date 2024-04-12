package biuropodrozy.gotravel.authenticate.dto.response;

import java.util.List;

/**
 * Represents the response object for authentication.
 * Contains the access token, refresh token, user ID, username, email, and roles.
 */
public record AuthResponse(String accessToken, String refreshToken, Long id, String username, String email, List<String> roles) { }
