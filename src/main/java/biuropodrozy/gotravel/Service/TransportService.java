package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Transport;

import java.util.List;
import java.util.Optional;

public interface TransportService {

    Optional<Transport> getTransportById(int idTransport);

    List<Transport> getAllTransports();
}
