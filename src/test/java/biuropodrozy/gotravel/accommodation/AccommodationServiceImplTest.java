package biuropodrozy.gotravel.accommodation;

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
class AccommodationServiceImplTest {

    @Mock
    private AccommodationRepository accommodationRepository;
    private AccommodationServiceImpl accommodationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accommodationService = new AccommodationServiceImpl(accommodationRepository);
    }

    @Test
    void testGetAllAccommodations() {
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setIdAccommodation(1);
        accommodation1.setNameAccommodation("Hotel czterogwiazdkowy");
        accommodation1.setPriceAccommodation(400.00);

        Accommodation accommodation2 = new Accommodation();
        accommodation2.setIdAccommodation(2);
        accommodation2.setNameAccommodation("Hotel dwugwiazdkowy");
        accommodation2.setPriceAccommodation(250.00);

        given(accommodationRepository.findAll()).willReturn(Arrays.asList(accommodation1, accommodation2));

        List<Accommodation> accommodations = accommodationService.getAllAccommodations();

        then(accommodationRepository).should(times(1)).findAll();
        assertAll("accomodations",
                () -> assertNotNull(accommodations),
                () -> assertEquals(2, accommodations.size()),
                () -> assertEquals("Hotel czterogwiazdkowy", accommodations.get(0).getNameAccommodation()),
                () -> assertEquals("Hotel dwugwiazdkowy", accommodations.get(1).getNameAccommodation()));
    }

    @Test
    void testGetAccommodationsById() {
        int id = 1;
        Accommodation accommodation = new Accommodation(id, "Hotel czterogwiazdkowy", 400.0, null, null);
        given(accommodationRepository.findByIdAccommodation(id)).willReturn(accommodation);

        Accommodation foundAccommodation = accommodationService.getAccommodationsById(id);

        then(accommodationRepository).should(times(1)).findByIdAccommodation(id);
        assertAll("foundAccommodation",
                () -> assertNotNull(foundAccommodation),
                () -> assertEquals("Hotel czterogwiazdkowy", foundAccommodation.getNameAccommodation())
        );
    }
}