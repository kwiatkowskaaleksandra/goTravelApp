package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Transport;

import java.util.List;

/**
 * The interface Transport service.
 */
public interface TransportService {

    /**
     * Get all transports.
     *
     * @return list of transports
     */
    List<Transport> getAllTransports();
}
