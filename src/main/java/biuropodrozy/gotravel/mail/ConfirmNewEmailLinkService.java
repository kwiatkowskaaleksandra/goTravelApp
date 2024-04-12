package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the TemplateDataStrategy interface for preparing template data related to confirm new email address.
 */
@Service("confirmNewEmailLink")
@Slf4j
public class ConfirmNewEmailLinkService implements TemplateDataStrategy {

    @Override
    public Map<String, Object> prepareTemplateData(User user, Object context) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        String expirationTimestamp = Long.toString(expirationTime.getEpochSecond());
        String encodedEmail = Base64.getEncoder().encodeToString(user.getEmail().getBytes(StandardCharsets.UTF_8));
        String encodedUsername = Base64.getEncoder().encodeToString(user.getUsername().getBytes(StandardCharsets.UTF_8));


        Map<String, Object> templateData = new HashMap<>();
        templateData.put("username", user.getUsername());
        templateData.put("code", "http://localhost:3000/confirmNewEmail?" +
                "email=" + encodedEmail +
                "&username=" + encodedUsername +
                "&expires=" + expirationTimestamp);
        return templateData;
    }
}
