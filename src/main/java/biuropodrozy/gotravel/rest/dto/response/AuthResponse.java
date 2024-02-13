package biuropodrozy.gotravel.rest.dto.response;

import java.util.List;

/**
 * The record of Auth response.
 * @param accessToken the access token
 */
public record AuthResponse(String accessToken, String refreshToken, Long id, String username, String email, List<String> roles) { }
