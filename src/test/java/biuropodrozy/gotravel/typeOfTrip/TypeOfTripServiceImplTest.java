package biuropodrozy.gotravel.typeOfTrip;

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
class TypeOfTripServiceImplTest {

    @Mock
    private TypeOfTripRepository typeOfTripRepository;
    private TypeOfTripServiceImpl typeOfTripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        typeOfTripService = new TypeOfTripServiceImpl(typeOfTripRepository);
    }

    @Test
    void getAllTypeOfTrips() {
        TypeOfTrip typeOfTrip1 = new TypeOfTrip();
        typeOfTrip1.setIdTypeOfTrip(1L);
        typeOfTrip1.setName("Last Minute");
        TypeOfTrip typeOfTrip2 = new TypeOfTrip();
        typeOfTrip2.setIdTypeOfTrip(2L);
        typeOfTrip2.setName("All Inclusive");

        given(typeOfTripRepository.findAll()).willReturn(Arrays.asList(typeOfTrip1, typeOfTrip2));

        List<TypeOfTrip> typeOfTrips = typeOfTripService.getAllTypeOfTrips();

        then(typeOfTripRepository).should(times(1)).findAll();
        assertAll("typeOfTrips",
                () -> assertNotNull(typeOfTrips),
                () -> assertEquals(2, typeOfTrips.size()),
                () -> assertEquals("Last Minute", typeOfTrips.get(0).getName()),
                () -> assertEquals("All Inclusive", typeOfTrips.get(1).getName()));
    }
}