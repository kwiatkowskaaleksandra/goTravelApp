package biuropodrozy.gotravel.transport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TransportService} interface.
 */
@RequiredArgsConstructor
@Service
public class TransportServiceImpl implements TransportService {

    /**
     * Repository for accessing and managing Transport entities.
     */
    private final TransportRepository transportRepository;

    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    @Override
    public Transport getTransportById(int idTransport) {
        return transportRepository.getReferenceById(idTransport);
    }
}
