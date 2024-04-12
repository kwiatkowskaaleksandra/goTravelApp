//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.photo.Photo;
//import biuropodrozy.gotravel.trip.Trip;
//import biuropodrozy.gotravel.photo.PhotoService;
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
//class PhotoControllerTest {
//
//    @Mock
//    private PhotoService photoService;
//
//    @InjectMocks
//    private PhotoController photoController;
//
//    @Test
//    void getAllPhotoByIdTrip() {
//        Trip trip = new Trip();
//        trip.setIdTrip(1L);
//        trip.setPrice(120.0);
//        trip.setFood("śniadanie");
//        trip.setNumberOfDays(12);
//
//        Photo photo1 = new Photo();
//        photo1.setIdPhoto(1L);
//        photo1.setUrlPhoto("https://www.tabletowo.pl/wp-content/uploads/2019/06/zdjecia-google-photos-1200x806.png");
//        photo1.setTrip(trip);
//
//        Photo photo2= new Photo();
//        photo2.setIdPhoto(2L);
//        photo2.setUrlPhoto("https://www.tabletowo.pl/wp-content/uploads/2019/06/zdjecia-google-photos-1200x806.png");
//        photo2.setTrip(trip);
//
//        List<Photo> photoList = new ArrayList<>();
//        photoList.add(photo1);
//        photoList.add(photo2);
//
//        when(photoService.getPhotosByIdTrip(1L)).thenReturn(photoList);
//        ResponseEntity<List<Photo>> response = photoController.getAllPhotoByIdTrip(1L);
//        HttpStatusCode status = response.getStatusCode();
//        List<Photo> photos = response.getBody();
//        assertEquals(status,  HttpStatusCode.valueOf(200));
//        assert photos != null;
//        assertEquals(photos.size(), photoList.size());
//    }
//}