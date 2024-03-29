package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.repository.TransportRepository;
import biuropodrozy.gotravel.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Transport service implementation.
 */
@RequiredArgsConstructor
@Service
public class TransportServiceImpl implements TransportService {

    /**
     * Repository for accessing and managing Transport entities.
     */
    private final TransportRepository transportRepository;

    /**
     * Get all transports.
     *
     * @return list of transports
     */
    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
}
