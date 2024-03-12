package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Insurance;
import biuropodrozy.gotravel.repository.InsuranceRepository;
import biuropodrozy.gotravel.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the InsuranceService interface that provides operations related to insurances.
 */
@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    /**
     * Data repository used for communicating with the database to perform operations on insurance data.
     */
    private final InsuranceRepository insuranceRepository;

    /**
     * Retrieves all insurances from the repository.
     *
     * @return List of all insurances
     */
    @Override
    public List<Insurance> getAll() {
        return insuranceRepository.findAll();
    }
}
