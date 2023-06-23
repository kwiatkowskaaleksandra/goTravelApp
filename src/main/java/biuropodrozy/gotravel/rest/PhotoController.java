package biuropodrozy.gotravel.rest;
import biuropodrozy.gotravel.model.Photo;
import biuropodrozy.gotravel.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Photo controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    /**
     * Service class for managing photos.
     */
    private final PhotoService photoService;

    /**
     * Get all photos by id trip response entity.
     *
     * @param idTrip the id trip
     * @return the list of photos response entity
     */
    @GetMapping("/{idTrip}")
    ResponseEntity<List<Photo>> getAllPhotoByIdTrip(@PathVariable final Long idTrip) {
        return ResponseEntity.ok(photoService.getPhotosByIdTrip(idTrip));
    }

}
