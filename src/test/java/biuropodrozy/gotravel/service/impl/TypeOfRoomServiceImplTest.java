package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.repository.TypeOfRoomRepository;
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
class TypeOfRoomServiceImplTest {

    @Mock
    private TypeOfRoomRepository typeOfRoomRepository;
    @InjectMocks
    private TypeOfRoomServiceImpl typeOfRoomService;
    private TypeOfRoom typeOfRoom;

    @BeforeEach
    public void setUp(){
        typeOfRoom = new TypeOfRoom();
        typeOfRoom.setIdTypeOfRoom(1);
        typeOfRoom.setType("pokój jednoosobowy");
        typeOfRoom.setRoomPrice(200.0);
    }

    @Test
    void getAllTypeOfRoom() {
        given(typeOfRoomRepository.findAll()).willReturn(List.of(typeOfRoom));
        List<TypeOfRoom> typeOfRoomList = typeOfRoomService.getAllTypeOfRoom();
        assertEquals(List.of(typeOfRoom).size(), typeOfRoomList.size());
    }

    @Test
    void getTypeOfRoom() {
        given(typeOfRoomRepository.findByIdTypeOfRoom(1)).willReturn(typeOfRoom);
        TypeOfRoom typeOfRoom1 = typeOfRoomService.getTypeOfRoom(1);
        assertEquals(typeOfRoom, typeOfRoom1);
    }

    @Test
    void getTypeOfRoomByType() {
        given(typeOfRoomRepository.findByType("pokój jednoosobowy")).willReturn(typeOfRoom);
        TypeOfRoom typeOfRoom1 = typeOfRoomService.getTypeOfRoomByType("pokój jednoosobowy");
        assertEquals(typeOfRoom, typeOfRoom1);
    }
}