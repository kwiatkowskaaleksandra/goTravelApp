package biuropodrozy.gotravel.photo;

import biuropodrozy.gotravel.trip.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class PhotoServiceImplTest {

    @Mock private PhotoRepository photoRepository;
    private PhotoServiceImpl photoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        photoService = new PhotoServiceImpl(photoRepository);
    }

    @Test
    void getPhotosByIdTrip() {
        Trip trip = new Trip();
        trip.setIdTrip(1L);
        Long idTrip = 1L;
        Photo photo1 = new Photo(1L, "www.zdjecie.com", trip);
        Photo photo2 = new Photo(2L, "www.zdjecie.com", trip);

        List<Photo> photos = new ArrayList<>(List.of(photo1, photo2));

        when(photoRepository.findAllByTrip_IdTrip(idTrip)).thenReturn(photos);

        List<Photo> result = photoService.getPhotosByIdTrip(idTrip);

        then(photoRepository).should(times(1)).findAllByTrip_IdTrip(idTrip);
        assertAll("result",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(photos, result));
    }

    @Test
    void savePhoto() {
        Photo photo = new Photo();
        photoService.savePhoto(photo);
        verify(photoRepository, times(1)).save(any(Photo.class));
    }

    @Test
    void deletePhotoForTrip() {
        Trip trip = new Trip();
        trip.setIdTrip(1L);
        Photo photo1 = new Photo(1L, "www.zdjecie.com", trip);
        Photo photo2 = new Photo(2L, "www.zdjecie.com", trip);

        List<Photo> photos = new ArrayList<>(List.of(photo1, photo2));
        when(photoRepository.findAllByTrip_IdTrip(trip.getIdTrip())).thenReturn(photos);

        photoService.deletePhotoForTrip(trip);
        verify(photoRepository, times(1)).deleteAll(photos);

    }
}