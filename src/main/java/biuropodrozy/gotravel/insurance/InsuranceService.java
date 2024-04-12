package biuropodrozy.gotravel.insurance;

import java.util.List;

/**
 * The interface for managing insurance-related operations.
 */
public interface InsuranceService {

    /**
     * Retrieves a list of all insurances.
     *
     * @return A list of all insurances
     */
    List<Insurance> getAll();
}
