package biuropodrozy.gotravel.security.oauth2;

import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * The GoogleOAuth2UserInfoExtractor class provides methods for extracting user details from a Google OAuth2 user and determining if the extractor can handle a specific OAuth2UserRequest.
 */
@Service
public class GoogleOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    /**
     * Extracts user details from the provided Google OAuth2User.
     *
     * @param oAuth2User The Google OAuth2User containing user information.
     * @return A UserDetailsImpl object representing the extracted user details.
     */
    @Override
    public UserDetailsImpl extractUserInfo(OAuth2User oAuth2User) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(retrieveAttr("email", oAuth2User));
        userDetails.setEmail(retrieveAttr("email", oAuth2User));
        userDetails.setFirstname(retrieveAttr("given_name", oAuth2User));
        userDetails.setProvider(OAuth2Provider.GOOGLE);
        userDetails.setAttributes(oAuth2User.getAttributes());
        userDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("USER")));
        userDetails.setActivity(true);
        
        return userDetails;
    }

    /**
     * Determines whether the extractor can handle the given OAuth2UserRequest for Google.
     *
     * @param userRequest The OAuth2UserRequest to be checked.
     * @return True if the extractor can handle the request, otherwise false.
     */
    @Override
    public boolean accepts(OAuth2UserRequest userRequest) {
        return OAuth2Provider.GOOGLE.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }

    /**
     * Retrieves an attribute from the OAuth2User, returning an empty string if not found.
     *
     * @param attr The attribute name to retrieve.
     * @param oAuth2User The OAuth2User containing user attributes.
     * @return The retrieved attribute value, or an empty string if not found.
     */
    private String retrieveAttr(String attr, OAuth2User oAuth2User) {
        Object attribute = oAuth2User.getAttributes().get(attr);
        return attribute == null ? "" : attribute.toString();
    }
}