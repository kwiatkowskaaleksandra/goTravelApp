package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.service.OpinionService;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OpinionControllerTest {

    @Mock
    private OpinionService opinionService;
    @Mock
    private UserService userService;
    @Mock
    private TripService tripService;
    @InjectMocks
    private OpinionController opinionController;
    private Opinion opinion;

    @BeforeEach
    void setUp() {
        Trip trip = new Trip();
        trip.setIdTrip(1L);

        User user = new User();
        user.setId(1L);

        opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setTrip(trip);
        opinion.setDescription("Opinia");
    }

    @Test
    void getAllOpinionByIdTrip() {
        when(opinionService.getOpinionsByIdTrip(1L)).thenReturn(List.of(opinion));
        ResponseEntity<List<Opinion>> response = opinionController.getAllOpinionByIdTrip(1L);
        List<Opinion> opinionList = response.getBody();
        assert opinionList != null;
        assertEquals(List.of(opinion).size(), opinionList.size());
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void createOpinion() {
        when(opinionService.saveOpinion(opinion)).thenReturn(opinion);
        ResponseEntity<Opinion> response = opinionController.createOpinion(1L, 1L, opinion);
        Opinion opinion1 = response.getBody();
        assertThat(opinion1).isNotNull();
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void deleteOpinion() {
        ArgumentCaptor<Opinion> argumentCaptor = ArgumentCaptor.forClass(Opinion.class);
        willDoNothing().given(opinionService).deleteOpinion(argumentCaptor.capture());
        ResponseEntity<?> response = opinionController.deleteOpinion(1);
        HttpStatusCode status = response.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
        assertEquals(response.getBody(), argumentCaptor.getValue());
    }
}