package biuropodrozy.gotravel.reservation;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservationControllerTest {

    private MockMvc mockMvc;
    @Mock private ReservationService reservationService;
    @Mock private AuthenticationHelper authenticationHelper;
    @InjectMocks private ReservationController reservationController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createReservationAuthorized() throws Exception{
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setIdReservation(1L);
        long expectedId = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(reservationService.saveReservation(any(Reservation.class), eq(user))).thenReturn(expectedId);

        mockMvc.perform(post("/api/reservations/addReservation")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("theTripHasBeenBooked"))
                .andExpect(jsonPath("$.idReservation").value(expectedId));

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService,times(1)).saveReservation(any(Reservation.class), any(User.class));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createReservationUnauthorized() throws Exception {
        Reservation reservation = new Reservation();

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/reservations/addReservation")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).saveReservation(any(Reservation.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updatePaymentStatusAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long reservation = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(put("/api/reservations/updatePaymentStatus")
                        .with(csrf())
                        .param("idOffer", String.valueOf(reservation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment status changed correctly."));

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, times(1)).updatePaymentStatus(reservation);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updatePaymentStatusUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/reservations/updatePaymentStatus")
                        .with(csrf())
                        .param("idOffer", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).updatePaymentStatus(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteReservationAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idOffer = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(delete("/api/reservations/deleteReservation/{idReservation}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, times(1)).deleteReservation(idOffer);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteReservationUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(delete("/api/reservations/deleteReservation/{idReservation}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).deleteReservation(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getReservationActiveOrdersAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        String period = "activeOrders";

        List<Reservation> reservations = new ArrayList<>(List.of(reservation));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(reservationService.getReservationActiveOrders(user, period)).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations/getReservationActiveOrders/{period}", period)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, times(1)).getReservationActiveOrders(user, period);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getReservationActiveOrdersUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/reservations/getReservationActiveOrders/{period}", "activeOrders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).getReservationActiveOrders(any(User.class), anyString());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getReservationInvoiceAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idOwnOffer = 1L;
        byte[] expectedPdfBytes = new byte[] {1, 2, 3, 4};

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(reservationService.getReservationInvoice(idOwnOffer)).thenReturn(expectedPdfBytes);

        mockMvc.perform(get("/api/reservations/getInvoice/{idReservation}", 1L)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().bytes(expectedPdfBytes));

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, times(1)).getReservationInvoice(idOwnOffer);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getReservationInvoiceUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/reservations/getInvoice/{idReservation}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).getReservationInvoice(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void getAllActiveReservationAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        int page = 0;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);
        Reservation sampleOffer = new Reservation();
        Page<Reservation> mockPage = new PageImpl<>(List.of(sampleOffer), pageable, 1);

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(reservationService.getAllActiveReservationNotAccepted(pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/api/reservations/getAllActiveReservationNotAccepted")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, times(1)).getAllActiveReservationNotAccepted(pageable);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void getAllActiveReservationUnauthorized() throws Exception {
        int page = 0;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/reservations/getAllActiveReservationNotAccepted")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).getAllActiveReservationNotAccepted(pageable);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void changeAcceptStatusAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idOffer = 1L;
        String acceptStatus= "accept";

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(put("/api/reservations/changeAcceptStatus")
                        .param("idOffer", String.valueOf(idOffer))
                        .param("acceptStatus", acceptStatus)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, times(1)).changeAcceptStatus(idOffer, acceptStatus);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void changeAcceptStatusUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/reservations/changeAcceptStatus")
                        .param("idOffer", String.valueOf(1))
                        .param("acceptStatus", "accept")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(reservationService, never()).changeAcceptStatus(anyLong(), anyString());
    }
}