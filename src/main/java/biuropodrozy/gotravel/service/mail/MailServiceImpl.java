package biuropodrozy.gotravel.service.mail;

import biuropodrozy.gotravel.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

/**
 * Implementation of the MailService interface for sending emails.
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    /**
     * The JavaMailSender instance for sending email messages.
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * The Freemarker Configuration for processing email templates.
     */
    @Autowired
    private Configuration freemarkerConfig;

    /**
     * TemplateDataStrategy instance for generating data required for updating email link templates.
     * This field is used to inject a TemplateDataStrategy instance specifically configured for generating data
     * required for updating email link templates.
     */
    @Autowired
    @Qualifier("updateEmailLink")
    private TemplateDataStrategy templateDataStrategyUpdateEmailLink;

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
    @Async
    @Override
    public void sendMail(String toEmail, String subject, String templateName, Map<String, Object> templateData) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            Template template = freemarkerConfig.getTemplate(templateName);
            String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateData);
            mailMessage.setText(emailContent, true);

            javaMailSender.send(message);
            log.info("Mail sent successfully.");
        } catch (Exception e) {
            log.error("Error while sending mail. " + e);
        }
    }

    /**
     * Sends a confirmation email for changing the email address of a user.
     * This method sends a confirmation email for changing the email address of the specified user.
     * It constructs an email message based on the 'confirmEmailTemplate.ftl' template and sends it
     * using the TemplateDataStrategy instance specifically configured for updating email link templates.
     *
     * @param user The user for whom the confirmation email is being sent.
     */
    @Override
    public void confirmationEmail(User user) {
        sendMail(
                user.getEmail(),
                "Confirm the change of email address",
                "confirmEmailTemplate.ftl",
                templateDataStrategyUpdateEmailLink.prepareTemplateData(user, null)
        );
    }
}