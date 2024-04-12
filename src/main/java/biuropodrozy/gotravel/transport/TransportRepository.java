package biuropodrozy.gotravel.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Transport repository.
 */
@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

}
