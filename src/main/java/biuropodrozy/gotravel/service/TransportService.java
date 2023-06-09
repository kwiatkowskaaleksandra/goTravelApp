package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Transport;

import java.util.List;

/**
 * The interface Transport service.
 */
public interface TransportService {

    /**
     * Get transport by id transport.
     *
     * @param idTransport the id transport
     * @return the transport
     */
    Transport getTransportById(int idTransport);

    /**
     * Get all transports.
     *
     * @return list of transports
     */
    List<Transport> getAllTransports();
}
