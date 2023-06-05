package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeOfRoomControllerTest {

    @Mock
    private TypeOfRoomService typeOfRoomService;
    @InjectMocks
    private TypeOfRoomController typeOfRoomController;
    private TypeOfRoom typeOfRoom;

    @BeforeEach
    public  void setUp(){
        typeOfRoom = new TypeOfRoom();
        typeOfRoom.setIdTypeOfRoom(1);
        typeOfRoom.setType("jednoosobowy");
        typeOfRoom.setRoomPrice(150.0);
    }

    @Test
    void getAllTypesOfRooms() {
        when(typeOfRoomService.getAllTypeOfRoom()).thenReturn(List.of(typeOfRoom));
        ResponseEntity<List<TypeOfRoom>> responseEntity = typeOfRoomController.getAllTypesOfRooms();
        List<TypeOfRoom> typeOfRoomList = responseEntity.getBody();
        assert typeOfRoomList != null;
        assertEquals(List.of(typeOfRoom).size(), typeOfRoomList.size());
        HttpStatusCode status = responseEntity.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
    }

    @Test
    void getTypeOfRoom() {
        when(typeOfRoomService.getTypeOfRoom(1)).thenReturn(typeOfRoom);
        ResponseEntity<TypeOfRoom> responseEntity = typeOfRoomController.getTypeOfRoom(1);
        TypeOfRoom type = responseEntity.getBody();
        assert type != null;
        assertEquals(typeOfRoom, type);
        HttpStatusCode status = responseEntity.getStatusCode();
        assertEquals(status, HttpStatusCode.valueOf(200));
    }
}