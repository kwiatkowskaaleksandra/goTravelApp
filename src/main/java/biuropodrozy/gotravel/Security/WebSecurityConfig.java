package biuropodrozy.gotravel.Security;/*
 * @project gotravel
 * @author kola
 */

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String USER = "USER";
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyAuthority(USER)
                .requestMatchers("/public/**", "/auth/**", "/gotravel/**").permitAll()
                .requestMatchers("/api/users/getUser/*").permitAll()
                .requestMatchers("/api/users/update/*").permitAll()
                .requestMatchers("/api/users/updatePassword/*").permitAll()
                .requestMatchers("/api/users/deleteUser/*").permitAll()

                .requestMatchers("/api/trips/all/*/*").permitAll()
                .requestMatchers("/api/trips/*").permitAll()
                .requestMatchers("/api/trips/byCity/*/*").permitAll()
                .requestMatchers("/api/trips/findByValues/*/*/*/*").permitAll()
                .requestMatchers("/api/trips/findByFilters/*/*/*/*/*/*/*").permitAll()
                .requestMatchers("/api/trips/all/*/*/*").permitAll()
                .requestMatchers("/api/trips/all/*").permitAll()
                .requestMatchers("/api/trip/photos/*").permitAll()

                .requestMatchers("/api/attractions/*").permitAll()
                .requestMatchers("/api/attractions/all").permitAll()

                .requestMatchers("/api/transport/all").permitAll()

                .requestMatchers("/api/country/all").permitAll()

                .requestMatchers("/api/cities/all/*").permitAll()

                .requestMatchers("/api/accommodations/all").permitAll()

                .requestMatchers("/api/opinions/*").permitAll()
                .requestMatchers("/api/opinions/addOpinion/*/*").permitAll()
                .requestMatchers("/api/opinions/deleteOpinion/*").permitAll()

                .requestMatchers("/api/photos/*").permitAll()
                .requestMatchers("/api/photos/trip/*").permitAll()


                .requestMatchers("/api/reservations/getReservation/*").permitAll()
                .requestMatchers("/api/reservations/getReservationByUser/*").permitAll()
                .requestMatchers("/api/reservations/addReservation/*/*").permitAll()
                .requestMatchers("/api/reservations/deleteReservation/*").permitAll()

                .requestMatchers("/api/reservationsTypOfRooms/getReservationsTypOfRooms/*").permitAll()
                .requestMatchers("/api/reservationsTypOfRooms/addReservationsTypOfRooms/*").permitAll()
                .requestMatchers("/api/typeOfRoom/all").permitAll()

                .requestMatchers("/api/ownOfferTypOfRooms/addOwnOfferTypeOfRooms/*").permitAll()
                .requestMatchers("/api/ownOffer/addOwnOfferAttractions").permitAll()
                .requestMatchers("/api/ownOffer/getByUsername/*").permitAll()
                .requestMatchers("/api/ownOffer/deleteOwnOffer/*").permitAll()
                .requestMatchers("/api/ownOffer/addOwnOffer/*").permitAll()


                .requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
