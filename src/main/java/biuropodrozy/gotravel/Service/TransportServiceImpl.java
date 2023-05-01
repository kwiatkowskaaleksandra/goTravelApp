package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Transport;
import biuropodrozy.gotravel.Repository.TransportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;

    @Override
    public Optional<Transport> getTransportById(int idTransport) {
        return transportRepository.findByIdTransport(idTransport);
    }

    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
}
