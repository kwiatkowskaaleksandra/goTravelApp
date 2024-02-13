package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Types of room controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/typeOfRoom")
public class TypeOfRoomController {

    /**
     * Service for managing TypeOfRoom-related operations.
     */
    private final TypeOfRoomService typeOfRoomService;

    /**
     * Get all types of rooms response entity.
     *
     * @return the list of types of rooms the response entity
     */
    @GetMapping("/all")
    ResponseEntity<List<TypeOfRoom>> getAllTypesOfRooms() {
        return ResponseEntity.ok(typeOfRoomService.getAllTypeOfRoom());
    }

}
