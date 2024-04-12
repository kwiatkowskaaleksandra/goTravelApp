package biuropodrozy.gotravel.insurance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for managing insurance-related endpoints.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    /**
     * The InsuranceService instance used for handling insurance-related operations.
     */
    private final InsuranceService insuranceService;

    /**
     * Retrieves all insurances.
     *
     * @return a ResponseEntity containing a list of all insurances
     */
    @GetMapping("/all")
    ResponseEntity<List<Insurance>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.getAll());
    }
}
