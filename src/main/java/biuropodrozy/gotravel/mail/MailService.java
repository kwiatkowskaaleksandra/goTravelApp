package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;

import java.util.Map;

/**
 * The MailService interface defines methods for preparing email template data and sending emails.
 */
public interface MailService {

    /**
     * Sends an email asynchronously.
     * This method sends an email using the provided parameters. It constructs an email message based on
     * the provided template and template data, and sends it using JavaMailSender.
     *
     * @param toEmail The recipient email address.
     * @param subject The subject of the email.
     * @param templateName The name of the template used for generating the email content.
     * @param templateData The data required for generating the email content.
     */
    void sendMail(String toEmail, String subject, String templateName, Map<String, Object> templateData);

    /**
     * Sends a confirmation email for changing the email address of a user.
     * This method sends a confirmation email for changing the email address of the specified user.
     * It constructs an email message based on the 'confirmEmailTemplate.ftl' template and sends it
     * using the TemplateDataStrategy instance specifically configured for updating email link templates.
     *
     * @param user The user for whom the confirmation email is being sent.
     */
    void confirmationEmail(User user);

}