package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.user.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@SpringBootTest
class MailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private Configuration freemarkerConfig;

    @Mock
    private TemplateDataStrategy templateDataStrategyUpdateEmailLink;

    @InjectMocks
    private MailServiceImpl mailService;

    @Mock private Template template;


    @BeforeEach
    public void setup() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        template = mock(Template.class);
        when(freemarkerConfig.getTemplate(anyString())).thenReturn(template);
    }

    @Test
    public void testConfirmationEmail() {
        User user = new User();
        user.setEmail("user@example.com");

        mailService.confirmationEmail(user);

        verify(templateDataStrategyUpdateEmailLink).prepareTemplateData(eq(user), isNull());
    }
}