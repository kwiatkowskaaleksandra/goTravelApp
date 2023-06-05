package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.OwnOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnOfferServiceImplTest {

    @Mock
    private OwnOfferRepository ownOfferRepository;
    @InjectMocks
    private OwnOfferServiceImpl ownOfferService;
    private OwnOffer ownOffer;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setId(1L);
        user.setUsername("janek12");

        ownOffer = new OwnOffer();
        ownOffer.setIdOwnOffer(1L);
        ownOffer.setNumberOfAdults(3);
        ownOffer.setNumberOfChildren(2);
        ownOffer.setUser(user);
    }

    @Test
    void saveOwnOffer() {
        given(ownOfferRepository.save(ownOffer)).willReturn(ownOffer);
        OwnOffer offer = ownOfferService.saveOwnOffer(ownOffer);
        assertThat(offer).isNotNull();
    }

    @Test
    void getOwnOfferByIdOwnOffer() {
        given(ownOfferRepository.findByIdOwnOffer(1L)).willReturn(ownOffer);
        OwnOffer offer = ownOfferService.getOwnOfferByIdOwnOffer(1L);
        assertThat(offer).isEqualTo(ownOffer);
    }

    @Test
    void getTopByOrderByIdOwnOffer() {
        given(ownOfferRepository.findTopByOrderByIdOwnOfferDesc()).willReturn(ownOffer);
        OwnOffer offer = ownOfferService.getTopByOrderByIdOwnOffer();
        assertThat(offer).isEqualTo(ownOffer);
    }

    @Test
    void getAllOwnOfferByUsername() {
        given(ownOfferRepository.findByUser_Username("janek12")).willReturn(List.of(ownOffer));
        List<OwnOffer> offer = ownOfferService.getAllOwnOfferByUsername("janek12");
        assertEquals(List.of(ownOffer).size(), offer.size());
    }

    @Test
    void deleteOwnOffer() {
        willDoNothing().given(ownOfferRepository).delete(ownOffer);
        ownOfferService.deleteOwnOffer(ownOffer);
        verify(ownOfferRepository, times(1)).delete(ownOffer);
    }
}