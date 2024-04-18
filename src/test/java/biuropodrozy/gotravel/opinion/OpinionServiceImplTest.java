package biuropodrozy.gotravel.opinion;

import biuropodrozy.gotravel.exception.OpinionException;
import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OpinionServiceImplTest {

    @Mock private OpinionRepository opinionRepository;
    private OpinionServiceImpl opinionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        opinionService = new OpinionServiceImpl(opinionRepository);
    }

    @Test
    void getCountOpinionsAndStars() {
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setStars(3.5);
        opinion.setDateOfAddingTheOpinion(LocalDate.now());
        opinion.setDescription("Great trip.");
        Long tripId = 1L;
        List<Opinion> opinions = List.of(opinion);
        when(opinionRepository.findByTrip_IdTrip(tripId)).thenReturn(opinions);

        Map<String, Object> results = opinionService.getCountOpinionsAndStars(tripId);

        assertEquals(1L, results.get("countNumberOfOpinion"));
        assertEquals(3.5, results.get("averageOpinionCalculated"));

    }

    @Test
    void getOpinionsByIdTrip() {
        Trip trip = new Trip();
        trip.setIdTrip(1L);
        User user = new User();
        user.setId(1L);
        Opinion opinion = new Opinion(1, "Great trip.", 3.5, LocalDate.now(), user, trip);
        Pageable pageable = PageRequest.of(0, 1);
        List<Opinion> opinions = new ArrayList<>(List.of(opinion));

        when(opinionRepository.findByTrip_IdTrip(trip.getIdTrip())).thenReturn(opinions);
        Page<Opinion> result = opinionService.getOpinionsByIdTrip(trip.getIdTrip(), "DESC", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(opinion, result.getContent().get(0));
    }

    @Test
    void saveOpinion_success() {
        User user = new User();
        user.setId(1L);
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setStars(3.5);
        opinion.setUser(user);
        opinion.setDateOfAddingTheOpinion(LocalDate.now());
        opinion.setDescription("Great trip.");

        when(opinionRepository.save(any(Opinion.class))).thenReturn(opinion);
        opinionService.saveOpinion(opinion, user);
        verify(opinionRepository, times(1)).save(opinion);
    }

    @Test
    void saveOpinion_starsEmpty() {
        User user = new User();
        user.setId(1L);
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setUser(user);
        opinion.setDateOfAddingTheOpinion(LocalDate.now());
        opinion.setDescription("Great trip.");

        OpinionException exception = assertThrows(OpinionException.class, () -> opinionService.saveOpinion(opinion, user));

        verify(opinionRepository, never()).save(any(Opinion.class));
        assertEquals("pleaseSelectTheNumberOfStars", exception.getMessage());
    }

    @Test
    void saveOpinion_starsDescription() {
        User user = new User();
        user.setId(1L);
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setUser(user);
        opinion.setDescription("");
        opinion.setDateOfAddingTheOpinion(LocalDate.now());
        opinion.setStars(3.5);

        OpinionException exception = assertThrows(OpinionException.class, () -> opinionService.saveOpinion(opinion, user));

        verify(opinionRepository, never()).save(any(Opinion.class));
        assertEquals("pleaseCompleteYourOpinion", exception.getMessage());
    }

    @Test
    void getOpinionByIdOpinion() {
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setDescription("");
        opinion.setDateOfAddingTheOpinion(LocalDate.now());
        opinion.setStars(3.5);
        int idOpinion = 1;

        when(opinionRepository.findOpinionByIdOpinion(idOpinion)).thenReturn(opinion);

        Opinion result = opinionService.getOpinionByIdOpinion(idOpinion);

        assertEquals(opinion, result);
    }

    @Test
    void deleteOpinion() {
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setDescription("");
        opinion.setDateOfAddingTheOpinion(LocalDate.now());
        opinion.setStars(3.5);
        int idOpinion = 1;

        when(opinionRepository.findOpinionByIdOpinion(idOpinion)).thenReturn(opinion);
        doNothing().when(opinionRepository).delete(opinion);

        opinionService.deleteOpinion(idOpinion);

        verify(opinionRepository, times(1)).delete(opinion);
    }

    @Test
    void countOpinionsByIdTrip() {
        Long tripId = 1L;
        when(opinionRepository.countOpinionByTrip_IdTrip(tripId)).thenReturn(10);

        int count = opinionService.countOpinionsByIdTrip(tripId);

        assertEquals(10, count);
    }
}