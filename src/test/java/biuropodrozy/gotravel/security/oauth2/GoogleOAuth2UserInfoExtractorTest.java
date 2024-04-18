package biuropodrozy.gotravel.security.oauth2;

import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoogleOAuth2UserInfoExtractorTest {

    private GoogleOAuth2UserInfoExtractor extractor;
    private OAuth2User mockOAuth2User;
    private OAuth2UserRequest mockOAuth2UserRequest;
    private ClientRegistration mockClientRegistration;

    @BeforeEach
    void setUp() {
        extractor = new GoogleOAuth2UserInfoExtractor();
        mockOAuth2User = mock(OAuth2User.class);
        mockOAuth2UserRequest = mock(OAuth2UserRequest.class);
        mockClientRegistration = mock(ClientRegistration.class);

        when(mockOAuth2User.getAttributes()).thenReturn(Map.of(
                "email", "test@example.com",
                "given_name", "TestName"
        ));

        when(mockOAuth2UserRequest.getClientRegistration()).thenReturn(mockClientRegistration);
        when(mockClientRegistration.getRegistrationId()).thenReturn("google");
    }

    @Test
    void extractUserInfo() {
        UserDetailsImpl userDetails = extractor.extractUserInfo(mockOAuth2User);

        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("TestName", userDetails.getFirstname());
        assertEquals(OAuth2Provider.GOOGLE, userDetails.getProvider());
        assertEquals(mockOAuth2User.getAttributes(), userDetails.getAttributes());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
        assertTrue(userDetails.isActivity());
    }

    @Test
    void testAcceptsValidRequest() {
        assertTrue(extractor.accepts(mockOAuth2UserRequest));
    }

    @Test
    void testDoesNotAcceptInvalidRequest() {
        when(mockClientRegistration.getRegistrationId()).thenReturn("other");
        assertFalse(extractor.accepts(mockOAuth2UserRequest));
    }
}