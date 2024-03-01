package biuropodrozy.gotravel.service.mail;

import biuropodrozy.gotravel.model.User;

import java.util.Map;

/**
 * Interface for preparing template data used in email content.
 */
public interface TemplateDataStrategy {

    /**
     * Prepares template data for the email.
     *
     * @param user The user for whom the email template data should be prepared.
     * @return A map containing the template data.
     */
    Map<String, Object> prepareTemplateData(User user, Object context);

}
