package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Opinion;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.OpinionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OpinionServiceImplTest {

    @Mock
    private OpinionRepository opinionRepository;
    @InjectMocks
    private OpinionServiceImpl opinionService;
    private Opinion opinion;

    @BeforeEach
    public void setUp(){
        Trip trip = new Trip();
        trip.setIdTrip(1L);

        User user = new User();
        user.setId(1L);

        opinion = new Opinion();
        opinion.setIdOpinion(1);
        opinion.setDescription("Opinia");
        opinion.setTrip(trip);
        opinion.setUser(user);
    }

    @Test
    void getOpinionsByIdTrip() {
        given(opinionRepository.findByTrip_IdTrip(1L)).willReturn(List.of(opinion));
        List<Opinion> opinionList = opinionService.getOpinionsByIdTrip(1L);
        assertEquals(List.of(opinion), opinionList);
    }

    @Test
    void saveOpinion() {
        given(opinionRepository.save(opinion)).willReturn(opinion);
        Opinion opinion1 = opinionService.saveOpinion(opinion);
        assertThat(opinion1).isNotNull();
    }

    @Test
    void getOpinionByIdOpinion() {
        given(opinionRepository.findOpinionByIdOpinion(1)).willReturn(opinion);
        Opinion opinion1 = opinionService.getOpinionByIdOpinion(1);
        assertEquals(opinion, opinion1);
    }

    @Test
    void deleteOpinion() {
        willDoNothing().given(opinionRepository).delete(opinion);
        opinionService.deleteOpinion(opinion);
        verify(opinionRepository, times(1)).delete(opinion);
    }

    @Test
    void getOpinionsByIdUser() {
        given(opinionRepository.getOpinionByUser_Id(1L)).willReturn(List.of(opinion));
        List<Opinion> opinionList = opinionService.getOpinionsByIdUser(1L);
        assertEquals(List.of(opinion), opinionList);
    }
}