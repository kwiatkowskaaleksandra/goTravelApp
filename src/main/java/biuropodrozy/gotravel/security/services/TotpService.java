package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.user.User;
import org.apache.commons.codec.binary.Base32;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * The type Totp service.
 */
@Service
public class TotpService {

    /**
     * Prefix for generating QR codes using the Google Chart API.
     */
    private static final String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    /**
     * Byte value used in calculations.
     */
    private static final int BYTE = 10;

    /**
     * Generate secret string.
     *
     * @return the string
     */
    public String generateSecret() {
        Base32 base32 = new Base32();
        byte[] bytes = new byte[BYTE];
        new SecureRandom().nextBytes(bytes);
        return base32.encodeAsString(bytes);
    }

    /**
     * Generate qr url string.
     *
     * @param user the user
     * @return the string
     */
    public String generateQRUrl(final User user) {
        return QR_PREFIX + URLEncoder.encode(String.format(
                        "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                        "GoTravel",
                        user.getUsername(),
                        user.getSecret2FA(),
                        "GoTravel"),
                StandardCharsets.UTF_8);
    }

    /**
     * Verify code boolean.
     *
     * @param secret the secret
     * @param code   the code
     * @return the boolean
     */
    public boolean verifyCode(final String secret, final int code) {
        Totp totp = new Totp(secret);
        return totp.verify(String.valueOf(code));
    }

}
