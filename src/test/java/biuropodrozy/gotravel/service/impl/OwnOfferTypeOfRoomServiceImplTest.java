//package biuropodrozy.gotravel.service.impl;
//
//import biuropodrozy.gotravel.model.OwnOffer;
//import biuropodrozy.gotravel.model.OwnOfferTypeOfRoom;
//import biuropodrozy.gotravel.repository.OwnOfferTypeOfRoomRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class OwnOfferTypeOfRoomServiceImplTest {
//
//    @Mock
//    private OwnOfferTypeOfRoomRepository ownOfferTypeOfRoomRepository;
//    @InjectMocks
//    private OwnOfferTypeOfRoomServiceImpl ownOfferTypeOfRoomService;
//    private OwnOfferTypeOfRoom ownOfferTypeOfRoom;
//
//    @BeforeEach
//    public void setup(){
//        OwnOffer offer = new OwnOffer();
//        offer.setIdOwnOffer(1L);
//
//        ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
//        ownOfferTypeOfRoom.setIdOwnOfferTypeOfRoom(1);
//        ownOfferTypeOfRoom.setNumberOfRoom(3);
//        ownOfferTypeOfRoom.setOwnOffer(offer);
//    }
//
//    @Test
//    void saveOwnOfferTypeOfRoom() {
//        given(ownOfferTypeOfRoomRepository.save(ownOfferTypeOfRoom)).willReturn(ownOfferTypeOfRoom);
//        OwnOfferTypeOfRoom room = ownOfferTypeOfRoomService.saveOwnOfferTypeOfRoom(ownOfferTypeOfRoom);
//        assertThat(room).isNotNull();
//    }
//
//    @Test
//    void findByOwnOffer_IdOwnOffer() {
//        given(ownOfferTypeOfRoomRepository.findByOwnOffer_IdOwnOffer(1L)).willReturn(List.of(ownOfferTypeOfRoom));
//        List<OwnOfferTypeOfRoom> rooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(1L);
//        assertEquals(List.of(ownOfferTypeOfRoom).size(), rooms.size());
//    }
//
//    @Test
//    void deleteOwnOfferTypeOfRoom() {
//        willDoNothing().given(ownOfferTypeOfRoomRepository).delete(ownOfferTypeOfRoom);
//        ownOfferTypeOfRoomService.deleteOwnOfferTypeOfRoom(ownOfferTypeOfRoom);
//        verify(ownOfferTypeOfRoomRepository, times(1)).delete(ownOfferTypeOfRoom);
//    }
//}