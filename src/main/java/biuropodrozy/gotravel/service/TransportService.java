package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Transport;

import java.util.List;
import java.util.Optional;

public interface TransportService {

    Transport getTransportById(int idTransport);

    List<Transport> getAllTransports();
}
