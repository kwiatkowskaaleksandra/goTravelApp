//package biuropodrozy.gotravel.rest;
//
//import biuropodrozy.gotravel.model.Attraction;
//import biuropodrozy.gotravel.model.OwnOffer;
//import biuropodrozy.gotravel.model.User;
//import biuropodrozy.gotravel.service.AttractionService;
//import biuropodrozy.gotravel.service.OwnOfferService;
//import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
//import biuropodrozy.gotravel.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class OwnOfferControllerTest {
//
//    @Mock
//    private OwnOfferService ownOfferService;
//    @Mock
//    private UserService userService;
//    @Mock
//    private AttractionService attractionService;
//    @Mock
//    private OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
//    @InjectMocks
//    private OwnOfferController ownOfferController;
//    private OwnOffer ownOffer;
//
//    @BeforeEach
//    public void setUp(){
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("krysia1234");
//
//        ownOffer = new OwnOffer();
//        ownOffer.setIdOwnOffer(1L);
//        ownOffer.setUser(user);
//    }
//
//    @Test
//    void createOwnOffer() {
//        when(ownOfferService.saveOwnOffer(ownOffer)).thenReturn(ownOffer);
//        ResponseEntity<OwnOffer> response = ownOfferController.createOwnOffer("krysia1234", ownOffer);
//        OwnOffer offer = response.getBody();
//        assertThat(offer).isNotNull();
//        HttpStatusCode status = response.getStatusCode();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//    }
//
//    @Test
//    void addAttractionsToOwnOffer() {
//        Attraction attraction = new Attraction();
//        attraction.setIdAttraction(1);
//        attraction.setNameAttraction("Przewodnik");
//        Set<Attraction> attractionSet = new HashSet<>();
//        attractionSet.add(attraction);
//        ownOffer.setOfferAttraction(attractionSet);
//
//        when(ownOfferService.getTopByOrderByIdOwnOffer()).thenReturn(ownOffer);
//        when(attractionService.getAttractionByNameAttraction(attraction.getNameAttraction())).thenReturn(Optional.of(attraction));
//        when(ownOfferService.getOwnOfferByIdOwnOffer(anyLong())).thenReturn(ownOffer);
//        ResponseEntity<?> response = ownOfferController.addAttractionsToOwnOffer("Przewodnik");
//        HttpStatusCode status = response.getStatusCode();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//        assertTrue(ownOffer.getOfferAttraction().stream().anyMatch(a -> a.getNameAttraction().equals(attraction.getNameAttraction())));
//        verify(ownOfferService).saveOwnOffer(ownOffer);
//    }
//
//    @Test
//    void getAllByUsername() {
//        when(ownOfferService.getAllOwnOfferByUsername("krysia1234")).thenReturn(List.of(ownOffer));
//        ResponseEntity<List<OwnOffer>> response = ownOfferController.getAllByUsername("krysia1234");
//        List<OwnOffer> ownOfferList = response.getBody();
//        assert ownOfferList != null;
//        assertEquals(List.of(ownOffer).size(), ownOfferList.size());
//        HttpStatusCode status = response.getStatusCode();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//    }
//
//    @Test
//    void deleteOwnOffer() {
//        ArgumentCaptor<OwnOffer> argumentCaptor = ArgumentCaptor.forClass(OwnOffer.class);
//        when(ownOfferService.getOwnOfferByIdOwnOffer(anyLong())).thenReturn(ownOffer);
//        willDoNothing().given(ownOfferService).deleteOwnOffer(argumentCaptor.capture());
//        ResponseEntity<?> response = ownOfferController.deleteOwnOffer(1L);
//        HttpStatusCode status = response.getStatusCode();
//        assertEquals(status, HttpStatusCode.valueOf(200));
//        assertEquals(ownOffer, argumentCaptor.getValue());
//    }
//}