package biuropodrozy.gotravel.service.mail;

import biuropodrozy.gotravel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the TemplateDataStrategy interface for preparing template data related to the confirmation of the change of e-mail address.
 */
@Service("updateEmailLink")
@Slf4j
public class UpdateEmailLinkService implements TemplateDataStrategy {

    @Override
    public Map<String, Object> prepareTemplateData(User user, Object context) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        String expirationTimestamp = Long.toString(expirationTime.getEpochSecond());
        String encodedEmail = Base64.getEncoder().encodeToString(user.getEmail().getBytes(StandardCharsets.UTF_8));

        Map<String, Object> templateData = new HashMap<>();
        try {
            templateData.put("username", user.getUsername());
            templateData.put("email", user.getEmail());
            templateData.put("code", "http://localhost:3000/confirm?email=" +
                    encodedEmail +
                    "&expires=" + expirationTimestamp);
            return templateData;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return templateData;
    }
}
