package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.repository.TransportRepository;
import biuropodrozy.gotravel.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;

    @Override
    public Transport getTransportById(int idTransport) {
        return transportRepository.findByIdTransport(idTransport);
    }

    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
}
