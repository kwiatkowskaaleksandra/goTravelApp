package biuropodrozy.gotravel.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;
    private TransportServiceImpl transportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transportService = new TransportServiceImpl(transportRepository);
    }

    @Test
    void getAllTransports() {
        Transport transport1 = new Transport();
        transport1.setIdTransport(1);
        transport1.setNameTransport("samolot");
        transport1.setPriceTransport(500.00);
        Transport transport2 = new Transport();
        transport2.setIdTransport(2);
        transport2.setNameTransport("bus");
        transport2.setPriceTransport(100.00);

        given(transportRepository.findAll()).willReturn(Arrays.asList(transport1, transport2));

        List<Transport> transports = transportService.getAllTransports();

        then(transportRepository).should(times(1)).findAll();
        assertAll("transports",
                () -> assertNotNull(transports),
                () -> assertEquals(2, transports.size()),
                () -> assertEquals("samolot", transports.get(0).getNameTransport()),
                () -> assertEquals("bus", transports.get(1).getNameTransport()));
    }

    @Test
    void getTransportById() {
        int id = 1;
        Transport transport = new Transport(1, "samolot", 500.00, null);
        given(transportRepository.getReferenceById(id)).willReturn(transport);

        Transport foundTransport = transportService.getTransportById(id);

        then(transportRepository).should(times(1)).getReferenceById(id);
        assertAll("foundTransport",
                () -> assertNotNull(foundTransport),
                () -> assertEquals("samolot", foundTransport.getNameTransport()));
    }
}