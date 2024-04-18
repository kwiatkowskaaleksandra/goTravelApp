package biuropodrozy.gotravel.ownOfferTypeOfRoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class OwnOfferTypeOfRoomServiceImplTest {

    @Mock private OwnOfferTypeOfRoomRepository ownOfferTypeOfRoomRepository;
    private OwnOfferTypeOfRoomServiceImpl ownOfferTypeOfRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ownOfferTypeOfRoomService = new OwnOfferTypeOfRoomServiceImpl(ownOfferTypeOfRoomRepository);
    }

    @Test
    void saveOwnOfferTypeOfRoom() {
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
        ownOfferTypeOfRoomService.saveOwnOfferTypeOfRoom(ownOfferTypeOfRoom);
        verify(ownOfferTypeOfRoomRepository).save(any(OwnOfferTypeOfRoom.class));
    }

    @Test
    void findByOwnOffer_IdOwnOffer() {
        Long idOwnOffer = 1L;
        List<OwnOfferTypeOfRoom> expectedList = new ArrayList<>();
        expectedList.add(new OwnOfferTypeOfRoom());

        when(ownOfferTypeOfRoomRepository.findOwnOfferTypeOfRoomByOwnOffer_IdOwnOffer(idOwnOffer)).thenReturn(expectedList);

        List<OwnOfferTypeOfRoom> resultList = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(idOwnOffer);

        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertEquals(expectedList, resultList);
        verify(ownOfferTypeOfRoomRepository).findOwnOfferTypeOfRoomByOwnOffer_IdOwnOffer(idOwnOffer);
    }

    @Test
    void deleteOwnOfferTypeOfRoom() {
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
        ownOfferTypeOfRoomService.deleteOwnOfferTypeOfRoom(ownOfferTypeOfRoom);
        verify(ownOfferTypeOfRoomRepository).delete(ownOfferTypeOfRoom);
    }
}