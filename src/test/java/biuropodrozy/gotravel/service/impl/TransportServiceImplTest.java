package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.repository.TransportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;
    @InjectMocks
    private TransportServiceImpl transportService;
    private Transport transport;

    @BeforeEach
    public void setUp(){
        transport = new Transport();
        transport.setIdTransport(1);
        transport.setNameTransport("samolot");
        transport.setPriceTransport(200.0);
    }

    @Test
    void getTransportById() {
        given(transportRepository.findByIdTransport(1)).willReturn(transport);
        Transport transport1 = transportService.getTransportById(1);
        assertEquals(transport, transport1);
    }

    @Test
    void getAllTransports() {
        given(transportRepository.findAll()).willReturn(List.of(transport));
        List<Transport> transportList = transportService.getAllTransports();
        assertThat(transportList.size()).isEqualTo(1);
    }
}