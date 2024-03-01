package biuropodrozy.gotravel.service.mail;

import biuropodrozy.gotravel.model.User;

import java.util.Map;

/**
 * The MailService interface defines methods for preparing email template data and sending emails.
 */
public interface MailService {

    /**
     * Sends an email with the specified details.
     *
     * @param user The recipient of the email.
     * @param subject The subject of the email.
     * @param templateName The name of the email template to be used.
     * @param templateData The template data to be used in the email template.
     */
    void sendMail(String user, String subject, String templateName, Map<String, Object> templateData);

    /**
     * Sends a confirmation email for changing the email address of a user.
     *
     * @param user The user for whom the confirmation email is being sent.
     */
    void confirmationEmail(User user);

}