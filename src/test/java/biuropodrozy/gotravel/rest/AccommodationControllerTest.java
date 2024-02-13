//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.model.Accommodation;
//import biuropodrozy.gotravel.service.AccommodationService;
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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AccommodationControllerTest {
//
//   @Mock
//   private AccommodationService accommodationService;
//
//   @InjectMocks
//   private AccommodationController accommodationController;
//
//    @Test
//    void readAllAccommodations() {
//
//        List<Accommodation> accommodationList = new ArrayList<>();
//
//        Accommodation a1 = new Accommodation();
//        a1.setIdAccommodation(1);
//        a1.setNameAccommodation("hotel");
//        a1.setPriceAccommodation(20.0);
//
//        accommodationList.add(a1);
//
//        when(accommodationService.getAllAccommodations()).thenReturn(accommodationList);
//
//        ResponseEntity<List<Accommodation>> response = accommodationController.readAllAccommodations();
//        HttpStatusCode status = response.getStatusCode();
//        List<Accommodation> accommodations = response.getBody();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//        assert accommodations != null;
//        assertEquals(accommodations.size(), accommodationList.size());
//    }
//}