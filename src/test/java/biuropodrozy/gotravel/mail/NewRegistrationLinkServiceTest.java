package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NewRegistrationLinkServiceTest {

    @InjectMocks private NewRegistrationLinkService newRegistrationLinkService;

    @BeforeEach
    void setUp() {
        newRegistrationLinkService = new NewRegistrationLinkService();
    }

    @Test
    void prepareTemplateData() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("TestUser");
        user.setVerificationRegisterCode("codeVerification");

        Map<String, Object> result = newRegistrationLinkService.prepareTemplateData(user, null);

        assertNotNull(result);
        assertTrue(result.containsKey("username"));
        assertTrue(result.containsKey("code"));

        String expectedUrl = String.format("http://localhost:3000/verify?code=%s", user.getVerificationRegisterCode());
        expectedUrl = expectedUrl.replace("%3D", "=");
        assertEquals(expectedUrl, result.get("code"));
    }
}