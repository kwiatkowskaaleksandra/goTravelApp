package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.security.services.TotpService;
import org.jboss.aerogear.security.otp.Totp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TotpServiceTest {

    private User user;
    @InjectMocks
    private TotpService totpService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("krysia1234");
        user.setPassword("krysia1234");
        user.setSecret2FA("mockedSecret");
    }

    @Test
    public void testGenerateSecret() {
        String secret = totpService.generateSecret();

        assertNotNull(secret);
        assertEquals(12, Base64.getDecoder().decode(secret).length);
    }

    @Test
    void generateQRUrl() {
        TotpService totpService = new TotpService();
        String qrUrl = totpService.generateQRUrl(user);
        assertThat(qrUrl).startsWith("https://chart.googleapis.com/chart");
    }

    @Test
    public void testVerifyCode() {
        String secret = totpService.generateSecret();
        Totp totp = new Totp(secret);
        String code = totp.now();
        assertTrue(totpService.verifyCode(secret, Integer.parseInt(code)));
    }
}