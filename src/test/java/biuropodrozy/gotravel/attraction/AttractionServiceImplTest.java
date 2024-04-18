package biuropodrozy.gotravel.attraction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
class AttractionServiceImplTest {

    @Mock
    private AttractionRepository attractionRepository;
    private AttractionServiceImpl attractionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attractionService = new AttractionServiceImpl(attractionRepository);
    }

    @Test
    void getAllByTrips_idTrip() {
        Long idTrip = 1L;
        Attraction attraction1 = new Attraction();
        attraction1.setIdAttraction(1);
        Attraction attraction2 = new Attraction();
        attraction2.setIdAttraction(2);

        List<Attraction> expectedAttractions = Arrays.asList(attraction1, attraction2);

        given(attractionRepository.findAllByTrips_idTrip(idTrip)).willReturn(expectedAttractions);

        List<Attraction> resultAttractions = attractionService.getAllByTrips_idTrip(idTrip);

        assertEquals(expectedAttractions, resultAttractions);

        then(attractionRepository).should(times(1)).findAllByTrips_idTrip(idTrip);
    }

    @Test
    void getAll() {
        Attraction attraction1 = new Attraction();
        attraction1.setIdAttraction(1);
        attraction1.setNameAttraction("Przewodnik po mieście");
        attraction1.setPriceAttraction(750.00);

        Attraction attraction2 = new Attraction();
        attraction2.setIdAttraction(2);
        attraction2.setNameAttraction("Animator dla dzieci");
        attraction2.setPriceAttraction(600.00);

        given(attractionRepository.findAll()).willReturn(Arrays.asList(attraction1, attraction2));

        List<Attraction> attractions = attractionService.getAll();

        then(attractionRepository).should(times(1)).findAll();
        assertAll("attractions",
                () -> assertNotNull(attractions),
                () -> assertEquals(2, attractions.size()),
                () -> assertEquals("Przewodnik po mieście", attractions.get(0).getNameAttraction()),
                () -> assertEquals("Animator dla dzieci", attractions.get(1).getNameAttraction()));
    }

    @Test
    void getAttractionById() {
        int id = 1;
        Attraction attraction = new Attraction(id, "Animator dla dzieci", 600.00, null, null);
        given(attractionRepository.findByIdAttraction(id)).willReturn(attraction);

        Attraction foundAttraction = attractionService.getAttractionById(id);

        then(attractionRepository).should(times(1)).findByIdAttraction(id);
        assertAll("foundAttraction",
                () -> assertNotNull(foundAttraction),
                () -> assertEquals("Animator dla dzieci", foundAttraction.getNameAttraction()));
    }
}