package biuropodrozy.gotravel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The type Web security config.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Represents the user role as a constant string value.
     */
    public static final String USER = "USER";
    /**
     * Creates a new instance of the TokenAuthenticationFilter class.
     */
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * AuthenticationManager configuration.
     *
     * @param authenticationConfiguration the authenticationConfiguration
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyAuthority(USER)

                .requestMatchers("/gotravel/**", "/api/**").permitAll()
                .requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers(headers ->
                        headers
                                .contentTypeOptions(withDefaults())
                                .xssProtection(withDefaults())
                                .cacheControl(withDefaults())
                                .httpStrictTransportSecurity(withDefaults())
                                .frameOptions(withDefaults()
                                )).cors(withDefaults())
                .csrf()
                .csrfTokenRepository(csrfTokenRepository());

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    /**
     * B crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Sets the header name that the CsrfToken is expected to appear on and the header that the response will contain the CsrfToken.
     *
     * @return The HttpSessionCsrfTokenRepository repository
     */
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        return repository;
    }

}
