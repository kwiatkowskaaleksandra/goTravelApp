package biuropodrozy.gotravel.service.mail;

import biuropodrozy.gotravel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the TemplateDataStrategy interface for preparing template data related to new registration link emails.
 */
@Service("newRegistrationLink")
@Slf4j
public class NewRegistrationLinkService implements TemplateDataStrategy {

    @Override
    public Map<String, Object> prepareTemplateData(User user, Object context) {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("username", user.getUsername());
        templateData.put("code", "http://localhost:3000/verify?code=" + user.getVerificationRegisterCode());
        return templateData;
    }

}