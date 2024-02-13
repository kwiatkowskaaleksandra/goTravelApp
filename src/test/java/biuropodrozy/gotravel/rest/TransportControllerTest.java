//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.model.Transport;
//import biuropodrozy.gotravel.service.TransportService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class TransportControllerTest {
//
//    @Mock
//    private TransportService transportService;
//
//    @InjectMocks
//    private TransportController transportController;
//
//    @Test
//    void readAllTransports() {
//        Transport transport1 = new Transport();
//        transport1.setIdTransport(1);
//        transport1.setNameTransport("samolot");
//        transport1.setPriceTransport(100.0);
//
//        Transport transport2 = new Transport();
//        transport2.setIdTransport(2);
//        transport2.setNameTransport("pociąg");
//        transport2.setPriceTransport(200.0);
//
//        List<Transport> transportList = new ArrayList<>();
//        transportList.add(transport1);
//        transportList.add(transport2);
//
//        when(transportService.getAllTransports()).thenReturn(transportList);
//
//        ResponseEntity<List<Transport>> response = transportController.readAllTransports();
//        HttpStatusCode status = response.getStatusCode();
//        List<Transport> transports = response.getBody();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//        assert transports != null;
//        assertEquals(transports.size(), transportList.size());
//    }
//}