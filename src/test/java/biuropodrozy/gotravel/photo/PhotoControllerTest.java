package biuropodrozy.gotravel.photo;

import biuropodrozy.gotravel.trip.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PhotoControllerTest {

    private MockMvc mockMvc;
    @Mock private PhotoService photoService;
    @InjectMocks PhotoController photoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
    }

    @Test
    void getAllPhotoByIdTrip() throws Exception {
        Trip trip = new Trip();
        trip.setIdTrip(1L);
        Photo photo1 = new Photo(1L, "www.zdjecie.com", trip);
        Photo photo2 = new Photo(2L, "www.zdjecie.com", trip);

        List<Photo> photos = new ArrayList<>(List.of(photo1, photo2));
        when(photoService.getPhotosByIdTrip(1L)).thenReturn(photos);

        mockMvc.perform(get("/api/photos/{idTrip}", trip.getIdTrip())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(photoService, times(1)).getPhotosByIdTrip(trip.getIdTrip());
    }
}