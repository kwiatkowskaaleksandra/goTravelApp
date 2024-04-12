package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

/**
 * Implementation of the {@link MailService} interface.
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    /**
     * The JavaMailSender instance for sending email messages.
     */
    private final JavaMailSender javaMailSender;

    /**
     * The Freemarker Configuration for processing email templates.
     */
    private final Configuration freemarkerConfig;

    /**
     * TemplateDataStrategy instance for generating data required for updating email link templates.
     * This field is used to inject a TemplateDataStrategy instance specifically configured for generating data
     * required for updating email link templates.
     */
    private final TemplateDataStrategy templateDataStrategyUpdateEmailLink;

    /**
     * Constructs a new MailServiceImpl with the specified dependencies.
     *
     * @param javaMailSender              the JavaMailSender used for sending emails
     * @param freemarkerConfig            the Freemarker configuration for template processing
     * @param templateDataStrategyUpdateEmailLink the strategy for generating template data for update email links
     */
    public MailServiceImpl(JavaMailSender javaMailSender, Configuration freemarkerConfig, @Qualifier("updateEmailLink") TemplateDataStrategy templateDataStrategyUpdateEmailLink) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
        this.templateDataStrategyUpdateEmailLink = templateDataStrategyUpdateEmailLink;
    }

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