package biuropodrozy.gotravel.insurance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link InsuranceService} interface.
 */
@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    /**
     * Data repository used for communicating with the database to perform operations on insurance data.
     */
    private final InsuranceRepository insuranceRepository;

    @Override
    public List<Insurance> getAll() {
        return insuranceRepository.findAll();
    }
}
