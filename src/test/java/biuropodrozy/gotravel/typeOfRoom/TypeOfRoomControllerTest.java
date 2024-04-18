package biuropodrozy.gotravel.typeOfRoom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class TypeOfRoomControllerTest {

    private MockMvc mockMvc;
    @Mock
    private TypeOfRoomService typeOfRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TypeOfRoomController typeOfRoomController = new TypeOfRoomController(typeOfRoomService);
        mockMvc = standaloneSetup(typeOfRoomController).build();
    }

    @Test
    void getAllTypesOfRooms() throws Exception {
        TypeOfRoom typeOfRoom1 = new TypeOfRoom(1, "Apartament", 950.00, null, null);
        TypeOfRoom typeOfRoom2 = new TypeOfRoom(2, "Pokój jednoosobowy", 180.00, null, null);
        given(typeOfRoomService.getAllTypeOfRoom()).willReturn(Arrays.asList(typeOfRoom1, typeOfRoom2));

        mockMvc.perform(get("/api/typeOfRoom/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(typeOfRoom1, typeOfRoom2))));

        verify(typeOfRoomService, times(1)).getAllTypeOfRoom();
    }
}