package biuropodrozy.gotravel.typeOfRoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
class TypeOfRoomServiceImplTest {

    @Mock
    private TypeOfRoomRepository typeOfRoomRepository;
    private TypeOfRoomServiceImpl typeOfRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        typeOfRoomService = new TypeOfRoomServiceImpl(typeOfRoomRepository);
    }

    @Test
    void getAllTypeOfRoom() {
        TypeOfRoom typeOfRoom1 = new TypeOfRoom();
        typeOfRoom1.setIdTypeOfRoom(1);
        typeOfRoom1.setType("Apartament");
        typeOfRoom1.setRoomPrice(950.00);
        TypeOfRoom typeOfRoom2 = new TypeOfRoom();
        typeOfRoom2.setIdTypeOfRoom(1);
        typeOfRoom2.setType("Pokój jednoosobowy");
        typeOfRoom2.setRoomPrice(180.00);

        given(typeOfRoomService.getAllTypeOfRoom()).willReturn(Arrays.asList(typeOfRoom1, typeOfRoom2));

        List<TypeOfRoom> typeOfRooms = typeOfRoomService.getAllTypeOfRoom();

        then(typeOfRoomRepository).should(times(1)).findAll();
        assertAll("typeOfRooms",
                () -> assertNotNull(typeOfRooms),
                () -> assertEquals(2, typeOfRooms.size()),
                () -> assertEquals("Apartament", typeOfRooms.get(0).getType()),
                () -> assertEquals("Pokój jednoosobowy", typeOfRooms.get(1).getType()));
    }

    @Test
    void getTypeOfRoomByType() {
        String type = "Apartament";
        TypeOfRoom typeOfRoom = new TypeOfRoom(1, "Apartament", 950.00, null, null);
        given(typeOfRoomRepository.findByType(type)).willReturn(typeOfRoom);

        TypeOfRoom foundTypeOfRoom = typeOfRoomService.getTypeOfRoomByType(type);

        then(typeOfRoomRepository).should(times(1)).findByType(type);
        assertAll("foundTypeOfRoom",
                () -> assertNotNull(foundTypeOfRoom),
                () -> assertEquals("Apartament", foundTypeOfRoom.getType()));
    }
}