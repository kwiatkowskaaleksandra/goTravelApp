package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.TypeOfRoom;
import biuropodrozy.gotravel.Service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/typeOfRoom")
public class TypeOfRoomController {

    private final TypeOfRoomService typeOfRoomService;

    @GetMapping("/all")
    ResponseEntity<List<TypeOfRoom>> getAllTypesOfRooms() {
        return ResponseEntity.ok(typeOfRoomService.getAllTypeOfRoom());
    }

    @GetMapping("/{idTypeOfRoom}")
    ResponseEntity<TypeOfRoom> getTypeOfRoom(@PathVariable int idTypeOfRoom) {
        return ResponseEntity.ok(typeOfRoomService.getTypeOfRoom(idTypeOfRoom));
    }

}
