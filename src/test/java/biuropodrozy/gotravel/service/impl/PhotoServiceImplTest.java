package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.photo.Photo;
import biuropodrozy.gotravel.photo.PhotoServiceImpl;
import biuropodrozy.gotravel.trip.Trip;
import biuropodrozy.gotravel.photo.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PhotoServiceImplTest {

    @Mock
    private PhotoRepository photoRepository;
    @InjectMocks
    private PhotoServiceImpl photoService;
    private Photo photo;

    @BeforeEach
    public void setUp(){
        Trip trip = new Trip();
        trip.setIdTrip(1L);

        photo = new Photo(1L, "https://bi.im-g.pl/im/ba/92/18/z25766074Q,wakacje.jpg", trip);
    }

    @Test
    void getPhotosByIdTrip() {
        given(photoRepository.findAllByTrip_IdTrip(1L)).willReturn(List.of(photo));
        List<Photo> photoList = photoService.getPhotosByIdTrip(1L);
        assertEquals(List.of(photo).size(), photoList.size());
    }
}