package biuropodrozy.gotravel.insurance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Insurance entities.
 */
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

}
