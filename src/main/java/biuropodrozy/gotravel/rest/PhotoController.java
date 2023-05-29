package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Photo;
import biuropodrozy.gotravel.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/{idTrip}")
    ResponseEntity<List<Photo>> getAllPhotoByIdTrip(@PathVariable Long idTrip) {
        return ResponseEntity.ok(photoService.getPhotosByIdTrip(idTrip));
    }

}
