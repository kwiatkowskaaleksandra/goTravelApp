package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Accommodation;
import biuropodrozy.gotravel.repository.AccommodationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceImplTest {

    @Mock
    private AccommodationRepository accommodationRepository;
    @InjectMocks
    private AccommodationServiceImpl accommodationService;
    private Accommodation accommodation;

    @BeforeEach
    public void setUp(){
        accommodation = new Accommodation();
        accommodation.setIdAccommodation(1);
        accommodation.setNameAccommodation("hotel");
        accommodation.setPriceAccommodation(230.90);
    }

    @Test
    void getAllAccommodations() {
        given(accommodationRepository.findAll()).willReturn(List.of(accommodation));
        List<Accommodation> accommodations1 = accommodationService.getAllAccommodations();
        assertThat(accommodations1.size()).isEqualTo(1);
    }
}