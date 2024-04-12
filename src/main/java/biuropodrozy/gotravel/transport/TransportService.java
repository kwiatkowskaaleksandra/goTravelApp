package biuropodrozy.gotravel.transport;

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

    /**
     * Retrieves the transport entity by its ID.
     *
     * @param idTransport the ID of the transport entity to retrieve
     * @return the transport entity with the specified ID, or null if not found
     */
    Transport getTransportById(int idTransport);
}
