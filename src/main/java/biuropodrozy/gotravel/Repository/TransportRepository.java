package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

    Optional<Transport> findByIdTransport(int idTransport);

    List<Transport> findAll();

}
