package biuropodrozy.gotravel.repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

    Transport findByIdTransport(int idTransport);

    List<Transport> findAll();

}
