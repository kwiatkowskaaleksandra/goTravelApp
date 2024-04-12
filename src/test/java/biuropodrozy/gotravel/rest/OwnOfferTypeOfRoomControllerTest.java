//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.ownOffer.OwnOffer;
//import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoom;
//import biuropodrozy.gotravel.typeOfRoom.TypeOfRoom;
//import biuropodrozy.gotravel.ownOffer.OwnOfferService;
//import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoomService;
//import biuropodrozy.gotravel.typeOfRoom.TypeOfRoomService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class OwnOfferTypeOfRoomControllerTest {
//
//    @Mock
//    private OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
//    @Mock
//    private OwnOfferService ownOfferService;
//    @Mock
//    private TypeOfRoomService typeOfRoomService;
//    @InjectMocks
//    private OwnOfferTypeOfRoomController ownOfferTypeOfRoomController;
//    private OwnOfferTypeOfRoom ownOfferTypeOfRoom;
//    private OwnOffer ownOffer;
//
//    @BeforeEach
//    void setUp() {
//        ownOffer = new OwnOffer();
//        ownOffer.setIdOwnOffer(1L);
//
//        TypeOfRoom typeOfRoom = new TypeOfRoom();
//        typeOfRoom.setIdTypeOfRoom(1);
//        typeOfRoom.setType("jednoosobowy");
//        typeOfRoom.setRoomPrice(200.0);
//
//        ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
//        ownOfferTypeOfRoom.setTypeOfRoom(typeOfRoom);
//        ownOfferTypeOfRoom.setIdOwnOfferTypeOfRoom(1);
//        ownOfferTypeOfRoom.setNumberOfRoom(4);
//        ownOfferTypeOfRoom.setOwnOffer(ownOffer);
//    }
//
//    @Test
//    void createNew() {
//        when(ownOfferTypeOfRoomService.saveOwnOfferTypeOfRoom(ownOfferTypeOfRoom)).thenReturn(ownOfferTypeOfRoom);
//        when(ownOfferService.getTopByOrderByIdOwnOffer()).thenReturn(ownOffer);
//        ResponseEntity<OwnOfferTypeOfRoom> response = ownOfferTypeOfRoomController.createNew("jednoosobowy", ownOfferTypeOfRoom);
//        OwnOfferTypeOfRoom ownOfferTypeOfRoom1 = response.getBody();
//        HttpStatusCode status = response.getStatusCode();
//        assertThat(ownOfferTypeOfRoom1).isNotNull();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//    }
//}