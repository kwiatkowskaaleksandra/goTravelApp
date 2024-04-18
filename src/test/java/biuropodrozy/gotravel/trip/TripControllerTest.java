package biuropodrozy.gotravel.trip;

import biuropodrozy.gotravel.authenticate.AuthenticationHelper;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.country.Country;
import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.transport.Transport;
import biuropodrozy.gotravel.trip.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.typeOfTrip.TypeOfTrip;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TripControllerTest {

    private MockMvc mockMvc;
    @Mock private TripService tripService;
    @Mock private AuthenticationHelper authenticationHelper;
    @InjectMocks private TripController tripController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();
    }

    @Test
    void readTripById() throws Exception {
        Trip trip = new Trip();
        trip.setTripDescription("Przetłumaczony opis");
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);

        mockMvc.perform(get("/api/trips/1/GB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripDescription").value("Translated description"));
    }

    @Test
    public void testReadTripByIdWithoutTranslation() throws Exception {
        Trip trip = new Trip();
        trip.setTripDescription("Test Description");
        when(tripService.getTripByIdTrip(1L)).thenReturn(trip);

        mockMvc.perform(get("/api/trips/1/PL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripDescription").value("Test Description"));
    }

    @Test
    void readAllTrips() throws Exception {
        String typeOfTrip = "Last Minute";
        Page<Trip> trips = new PageImpl<>(List.of(new Trip(), new Trip()));
        Pageable paging = PageRequest.of(0, 2);

        when(tripService.getTripsByTypeOfTrip(typeOfTrip, paging)).thenReturn(trips);

        mockMvc.perform(get("/api/trips/all/{typeOfTrip}", typeOfTrip)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(2))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void countTripsByTypeOfTrip() throws Exception {
        String typeOfTrip = "Last Minute";
        int count = 10;

        when(tripService.countTripByTypeOfTrip(typeOfTrip)).thenReturn(count);

        mockMvc.perform(get("/api/trips/countTrips/{typeOfTrip}", typeOfTrip))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void filterByCountryTransportNumberOfDays() throws Exception {
        TypeOfTrip typeOfTrip = new TypeOfTrip();
        typeOfTrip.setName("Last Minute");
        Country country = new Country();
        country.setIdCountry(1);
        City city = new City();
        city.setIdCity(1);
        city.setCountry(country);
        Transport transport = new Transport();
        transport.setIdTransport(1);
        Trip trip1 = new Trip();
        trip1.setTypeOfTrip(typeOfTrip);
        trip1.setTripCity(city);
        trip1.setTripTransport(transport);
        trip1.setPrice(200);
        trip1.setNumberOfDays(4);
        Trip trip2 = new Trip();
        trip2.setTypeOfTrip(typeOfTrip);
        trip2.setTripCity(city);
        trip2.setTripTransport(transport);
        trip2.setPrice(200);
        trip2.setNumberOfDays(4);

        TripFilteringRequest request = new TripFilteringRequest("lastMinute", 1, 1, 100.0, 500.0, 3, 7);

        when(tripService.filteringTrips(any(TripFilteringRequest.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(new Trip(), new Trip())));


        mockMvc.perform(get("/api/trips/findByFilters")
                        .param("typeOfTrip", request.getTypeOfTrip())
                        .param("idCountry", String.valueOf(request.getIdCountry()))
                        .param("typeOfTransport", String.valueOf(request.getTypeOfTransport()))
                        .param("minPrice", String.valueOf(request.getMinDays()))
                        .param("maxPrice", String.valueOf(request.getMaxPrice()))
                        .param("minDays", String.valueOf(request.getMinDays()))
                        .param("maxDays", String.valueOf(request.getMaxDays()))
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.size").value(2));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void tripRecommendationUnauthorized() throws Exception {
        User user = new User();
        UserTripPreferences userTripPreferences = new UserTripPreferences(1L, 2.0, 4.0, 4.0, 1, 3, user);
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/trips/tripRecommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userTripPreferences)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, never()).tripRecommendation(any(UserTripPreferences.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void tripRecommendationAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_USER);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        UserTripPreferences userTripPreferences = new UserTripPreferences(1L, 2.0, 4.0, 4.0, 1, 3, user);

        List<Trip> trips = tripService.tripRecommendation(userTripPreferences);

        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(post("/api/trips/tripRecommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userTripPreferences)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(trips));

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, times(1)).tripRecommendation(userTripPreferences);
    }

    @Test
    void getMostBookedTrips() throws Exception {
        List<Trip> mostBookedTrips = new ArrayList<>();
        mostBookedTrips.add(new Trip());
        mostBookedTrips.add(new Trip());

        when(tripService.getMostBookedTrips()).thenReturn(mostBookedTrips);

        mockMvc.perform(get("/api/trips/mostBookedTrips")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void validateAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Trip trip = new Trip();
        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        double totalPrice = tripService.validate(trip);

        mockMvc.perform(post("/api/trips/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(totalPrice));

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, times(1)).validate(trip);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void validateUnauthorized() throws Exception {
        Trip trip = new Trip();
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/trips/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, never()).validate(any(Trip.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void saveNewTripAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Trip trip = new Trip();
        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        tripService.saveTrip(trip);

        mockMvc.perform(post("/api/trips/saveNewTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, times(1)).saveTrip(trip);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void saveNewTripUnauthorized() throws Exception {
        Trip trip = new Trip();
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(post("/api/trips/saveNewTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, never()).saveTrip(any(Trip.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void deleteTheOfferAuthorized() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setName(RoleEnum.ROLE_MODERATOR);
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(role));
        Long idTrip = 1L;
        when(authenticationHelper.validateAuthentication()).thenReturn(user);

        mockMvc.perform(delete("/api/trips/deleteTheOffer/{idTrip}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, times(1)).deleteTheOffer(idTrip);
    }

    @Test
    @WithMockUser(username = "user", roles = "MODERATOR")
    void deleteTheOfferUnauthorized() throws Exception {
        Long idTrip = 1L;
        when(authenticationHelper.validateAuthentication()).thenReturn(null);

        mockMvc.perform(delete("/api/trips/deleteTheOffer/{idTrip}", idTrip)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authenticationHelper).validateAuthentication();
        verify(tripService, never()).deleteTheOffer(anyLong());
    }
}