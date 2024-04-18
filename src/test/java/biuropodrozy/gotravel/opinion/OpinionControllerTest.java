package biuropodrozy.gotravel.opinion;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.user.User;
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

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OpinionControllerTest {

    private MockMvc mockMvc;
    @Mock private OpinionService opinionService;
    @Mock private AuthenticationHelper authenticationHelper;
    @InjectMocks private OpinionController opinionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(opinionController).build();
    }

    @Test
    void getCountOpinionsAndStars() throws Exception {
        Long idTrip = 1L;
        Map<String, Object> mockResult = Map.of("countNumberOfOpinion", 10, "averageOpinionCalculated", 4.5);
        when(opinionService.getCountOpinionsAndStars(idTrip)).thenReturn(mockResult);

        mockMvc.perform(get("/api/opinions/countOpinionsAndStars")
                        .param("idTrip", idTrip.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countNumberOfOpinion").value(10))
                .andExpect(jsonPath("$.averageOpinionCalculated").value(4.5));
    }

    @Test
    void getAllOpinionByIdTrip() throws Exception {
        Long idTrip = 1L;
        String sortType = "DESC";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Opinion> opinionsPage = new PageImpl<>(List.of(new Opinion()));

        when(opinionService.getOpinionsByIdTrip(idTrip, sortType, pageable)).thenReturn(opinionsPage);

        mockMvc.perform(get("/api/opinions/{idTrip}", idTrip)
                        .param("sortType", sortType)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void countOpinionsByIdTrip() throws Exception {
        Long idTrips = 1L;
        when(opinionService.countOpinionsByIdTrip(idTrips)).thenReturn(20);

        mockMvc.perform(get("/api/opinions/countOpinions/{idTrips}", idTrips))
                .andExpect(status().isOk())
                .andExpect(content().string("20"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createOpinionAuthorized() throws Exception {
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(post("/api/opinions/addOpinion")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Great trip.\", \"stars\": 4.5, \"trip\":  {\"idTrip\": 1}}"))
                .andExpect(status().isOk());

        verify(opinionService, times(1)).saveOpinion(any(Opinion.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createOpinionUnauthorized() throws Exception {

        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/opinions/addOpinion")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Great trip.\", \"stars\": 4.5, \"trip\":  {\"idTrip\": 1}}"))
                .andExpect(status().isUnauthorized());

        verify(opinionService, never()).saveOpinion(any(Opinion.class), any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteOpinionAuthorized() throws Exception {
        int idOpinion = 1;
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(delete("/api/opinions/deleteOpinion/{idOpinion}", idOpinion)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(opinionService, times(1)).deleteOpinion(idOpinion);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteOpinionUnauthorized() throws Exception {
        int idOpinion = 1;
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(delete("/api/opinions/deleteOpinion/{idOpinion}", idOpinion)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(opinionService, never()).deleteOpinion(idOpinion);
    }
}