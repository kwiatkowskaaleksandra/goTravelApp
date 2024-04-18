package biuropodrozy.gotravel.insurance;

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
class InsuranceServiceImplTest {

    @Mock
    private InsuranceRepository insuranceRepository;
    private InsuranceServiceImpl insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        insuranceService = new InsuranceServiceImpl(insuranceRepository);
    }

    @Test
    void getAll() {
        Insurance insurance1 = new Insurance();
        insurance1.setIdInsurance(1);
        insurance1.setName("Standardowe");
        insurance1.setPrice(129.99);
        Insurance insurance2 = new Insurance();
        insurance2.setIdInsurance(1);
        insurance2.setName("Rozszerzone");
        insurance2.setPrice(189.99);

        given(insuranceRepository.findAll()).willReturn(Arrays.asList(insurance1, insurance2));

        List<Insurance> insurances = insuranceService.getAll();

        then(insuranceRepository).should(times(1)).findAll();
        assertAll("insurances",
                () -> assertNotNull(insurances),
                () -> assertEquals(2, insurances.size()),
                () -> assertEquals("Standardowe", insurances.get(0).getName()),
                () -> assertEquals("Rozszerzone", insurances.get(1).getName()));
    }
}