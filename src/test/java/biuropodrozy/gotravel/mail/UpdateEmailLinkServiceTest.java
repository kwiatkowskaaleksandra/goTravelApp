package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UpdateEmailLinkServiceTest {

    @InjectMocks private UpdateEmailLinkService updateEmailLinkService;

    @BeforeEach
    void setUp() {
        updateEmailLinkService = new UpdateEmailLinkService();
    }

    @Test
    void prepareTemplateData() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("TestUser");

        Map<String, Object> templateData = updateEmailLinkService.prepareTemplateData(user, null);

        assertEquals(user.getUsername(), templateData.get("username"));
        assertEquals(user.getEmail(), templateData.get("email"));
        assertTrue(templateData.containsKey("code"));

        String expectedEncodedEmail = Base64.getEncoder().encodeToString(user.getEmail().getBytes());

        String expectedCode = "http://localhost:3000/confirm?email=" + expectedEncodedEmail +
                "&expires=" + (Instant.now().plus(1, ChronoUnit.HOURS).getEpochSecond());

        assertEquals(expectedCode, templateData.get("code"));
    }
}