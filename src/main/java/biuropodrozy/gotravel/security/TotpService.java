package biuropodrozy.gotravel.security;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.User;
import org.apache.commons.codec.binary.Base32;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Service
public class TotpService {

    private static final String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    private static final int BYTE = 10;
    public String generateSecret() {
        Base32 base32 = new Base32();
        byte[] bytes = new byte[BYTE];
        new SecureRandom().nextBytes(bytes);
        return base32.encodeAsString(bytes);
    }
    public String generateQRUrl(User user) {
        return QR_PREFIX + URLEncoder.encode(String.format(
                        "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                        "SpringSecurity",
                        user.getUsername(),
                        user.getSecret2FA(),
                        "SpringSecurity"),
                StandardCharsets.UTF_8);
    }
    public boolean verifyCode(String secret, int code) {
        Totp totp = new Totp(secret);
        return totp.verify(String.valueOf(code));
    }

}
