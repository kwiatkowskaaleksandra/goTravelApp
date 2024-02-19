package biuropodrozy.gotravel.security.oauth2;

import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * The OAuth2UserInfoExtractor interface defines methods for extracting user details from OAuth2User objects and determining if the extractor can handle a specific OAuth2UserRequest.
 */
public interface OAuth2UserInfoExtractor {

    /**
     * Extracts user details from the provided OAuth2User object.
     *
     * @param oAuth2User The OAuth2User object containing user information.
     * @return A UserDetailsImpl object representing the extracted user details.
     */
    UserDetailsImpl extractUserInfo(OAuth2User oAuth2User);

    /**
     * Determines whether the extractor can handle the given OAuth2UserRequest.
     *
     * @param userRequest The OAuth2UserRequest to be checked.
     * @return True if the extractor can handle the request, otherwise false.
     */
    boolean accepts(OAuth2UserRequest userRequest);
}
