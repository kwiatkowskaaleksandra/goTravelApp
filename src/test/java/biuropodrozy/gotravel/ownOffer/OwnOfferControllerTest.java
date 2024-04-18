package biuropodrozy.gotravel.ownOffer;

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

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OwnOfferControllerTest {

    private MockMvc mockMvc;
    @Mock private OwnOfferService ownOfferService;
    @Mock private AuthenticationHelper authenticationHelper;
    @InjectMocks private OwnOfferController ownOfferController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ownOfferController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getTotalPriceAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setUser(user);
        double expectedPrice = 3110.00;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(ownOfferService.getTotalPrice(any(OwnOffer.class), any(User.class))).thenReturn(expectedPrice);

        mockMvc.perform(post("/api/ownOffer/getTotalPrice")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ownOffer)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedPrice)));

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).getTotalPrice(any(OwnOffer.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getTotalPriceUnauthorized() throws Exception {
        OwnOffer ownOffer = new OwnOffer();

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/ownOffer/getTotalPrice")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownOffer)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).getTotalPrice(any(OwnOffer.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createOwnOfferAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setUser(user);
        ownOffer.setIdOwnOffer(1L);
        long expectedId = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(ownOfferService.saveOwnOffer(any(OwnOffer.class), eq(user))).thenReturn(expectedId);

        mockMvc.perform(post("/api/ownOffer/createOwnOffer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownOffer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("theTripHasBeenBooked"))
                .andExpect(jsonPath("$.idOwnOffer").value(expectedId));

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService,times(1)).saveOwnOffer(any(OwnOffer.class), any(User.class));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createOwnOfferUnauthorized() throws Exception {
        OwnOffer ownOffer = new OwnOffer();

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/ownOffer/createOwnOffer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownOffer)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).saveOwnOffer(any(OwnOffer.class), any(User.class));
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
        Long idOwnOffer = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(put("/api/ownOffer/updatePaymentStatus")
                        .with(csrf())
                        .param("idOwnOffer", String.valueOf(idOwnOffer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment status changed correctly."));

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).updatePaymentStatus(idOwnOffer);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updatePaymentStatusUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/ownOffer/updatePaymentStatus")
                        .with(csrf())
                        .param("idOwnOffer", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).updatePaymentStatus(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getOwnOffersActiveOrdersAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setUser(user);
        String period = "activeOrders";

        List<OwnOffer> ownOffers = new ArrayList<>(List.of(ownOffer));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(ownOfferService.getOwnOffersActiveOrders(user, period)).thenReturn(ownOffers);

        mockMvc.perform(get("/api/ownOffer/getOwnOffersActiveOrders/{period}", period)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).getOwnOffersActiveOrders(user, period);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getOwnOffersActiveOrdersUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/ownOffer/getOwnOffersActiveOrders/{period}", "activeOrders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).getOwnOffersActiveOrders(any(User.class), anyString());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteOwnOfferAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idOffer = 1L;

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(delete("/api/ownOffer/deleteReservation/{idOwnOffer}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).deleteOwnOffer(idOffer);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteOwnOfferUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(delete("/api/ownOffer/deleteReservation/{idOwnOffer}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).deleteOwnOffer(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getOwnOfferInvoiceAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idOwnOffer = 1L;
        byte[] expectedPdfBytes = new byte[] {1, 2, 3, 4};

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(ownOfferService.getReservationInvoice(idOwnOffer)).thenReturn(expectedPdfBytes);

        mockMvc.perform(get("/api/ownOffer/getInvoice/{idOwnOffer}", 1L)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().bytes(expectedPdfBytes));

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).getReservationInvoice(idOwnOffer);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getOwnOfferInvoiceUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/ownOffer/getInvoice/{idOwnOffer}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).getReservationInvoice(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void getAllActiveOwnOffersAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        int page = 0;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);
        OwnOffer sampleOffer = new OwnOffer();
        Page<OwnOffer> mockPage = new PageImpl<>(List.of(sampleOffer), pageable, 1);

        when(authenticationHelper.validateAuthentication()).thenReturn(user);
        when(ownOfferService.getAllActiveOwnOffersNotAccepted(pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/api/ownOffer/getAllActiveOwnOffersNotAccepted")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).getAllActiveOwnOffersNotAccepted(pageable);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void getAllActiveOwnOffersUnauthorized() throws Exception {
        int page = 0;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(get("/api/ownOffer/getAllActiveOwnOffersNotAccepted")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).getAllActiveOwnOffersNotAccepted(pageable);
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

        mockMvc.perform(put("/api/ownOffer/changeAcceptStatus")
                        .param("idOffer", String.valueOf(idOffer))
                        .param("acceptStatus", acceptStatus)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, times(1)).changeAcceptStatus(idOffer, acceptStatus);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void changeAcceptStatusUnauthorized() throws Exception {
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(put("/api/ownOffer/changeAcceptStatus")
                        .param("idOffer", String.valueOf(1))
                        .param("acceptStatus", "accept")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(ownOfferService, never()).changeAcceptStatus(anyLong(), anyString());
    }
}