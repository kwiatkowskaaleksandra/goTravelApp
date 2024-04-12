package biuropodrozy.gotravel.security;

import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.security.services.TotpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TotpServiceTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("krysia1234");
        user.setPassword("krysia1234");
        user.setSecret2FA("mockedSecret");
    }


    @Test
    void generateQRUrl() {
        TotpService totpService = new TotpService();
        String qrUrl = totpService.generateQRUrl(user);
        assertThat(qrUrl).startsWith("https://chart.googleapis.com/chart");
    }
}