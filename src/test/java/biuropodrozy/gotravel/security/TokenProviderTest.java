//package biuropodrozy.gotravel.security;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtParser;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.context.TestPropertySource;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//@TestPropertySource(properties = "gotravel.app.jwtSecret=2922fef04e926e7eabc0c9a989c3393db47e4e45f94d66e57f95283ea14189d7ac6d7fd0840529e89c31bef64e3073e30b0fbd2be518bf906795b9ab3b8d5394")
//class TokenProviderTest {
//    @Mock
//    private Authentication authentication;
//    @Mock
//    private Claims claims;
//
//    @Mock
//    private JwtParser jwtParser;
//
//    @Mock
//    private Jws<Claims> jws;
//    @Autowired
//    private TokenProvider tokenProvider;
//    private static final String TOKEN = "valid-token";
//
//    @Test
//    void generate() {
//        UserDetailsImpl user = new UserDetailsImpl();
//        when(authentication.getPrincipal()).thenReturn(user);
//        String token = tokenProvider.generate(authentication);
//        assertThat(token).isNotNull();
//    }
//}