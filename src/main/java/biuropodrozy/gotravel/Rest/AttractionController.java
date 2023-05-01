package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Attraction;
import biuropodrozy.gotravel.Model.Trip;
import biuropodrozy.gotravel.Service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping(value = "/{idTrip}")
    ResponseEntity<List<Attraction>> readAllTrips(@PathVariable Long idTrip){
        return ResponseEntity.ok(attractionService.getAllByTrips_idTrip(idTrip));
    }

}
