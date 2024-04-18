package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmNewEmailLinkServiceTest {

    @InjectMocks
    private ConfirmNewEmailLinkService confirmNewEmailLinkService;

    @BeforeEach
    public void setUp() {
        confirmNewEmailLinkService = new ConfirmNewEmailLinkService();
    }

    @Test
    public void testPrepareTemplateData() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("TestUser");
        String expectedEmail = URLEncoder.encode(Base64.getEncoder().encodeToString("user@example.com".getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        String expectedUsername = URLEncoder.encode(Base64.getEncoder().encodeToString("TestUser".getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

        Map<String, Object> result = confirmNewEmailLinkService.prepareTemplateData(user, null);

        assertNotNull(result);
        assertTrue(result.containsKey("username"));
        assertTrue(result.containsKey("code"));
        assertEquals("TestUser", result.get("username"));
        String expectedUrl = String.format("http://localhost:3000/confirmNewEmail?email=%s&username=%s&expires=%s", expectedEmail, expectedUsername, result.get("code").toString().split("&expires=")[1]);
        expectedUrl = expectedUrl.replace("%3D", "=");
        assertEquals(expectedUrl, result.get("code"));
    }
}
