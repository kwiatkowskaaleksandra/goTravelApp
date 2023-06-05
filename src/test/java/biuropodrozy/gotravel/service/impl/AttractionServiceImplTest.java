package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Attraction;
import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.repository.AttractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AttractionServiceImplTest {

    @Mock
    AttractionRepository attractionRepository;
    @InjectMocks
    private AttractionServiceImpl attractionService;
    private Attraction attraction;

    @BeforeEach
    public void setUp() {
        attraction = new Attraction();
        attraction.setIdAttraction(1);
        attraction.setNameAttraction("Przewodnik");
        attraction.setPriceAttraction(120.0);
    }

    @Test
    void getAllByTrips_idTrip() {
        Trip trip = new Trip();
        trip.setIdTrip(1L);
        trip.setNumberOfDays(12);
        Set<Trip> tripSet = new HashSet<>();
        tripSet.add(trip);
        attraction.setTrips(tripSet);

        given(attractionRepository.findAllByTrips_idTrip(1L)).willReturn(List.of(attraction));
        List<Attraction> attractionList = attractionService.getAllByTrips_idTrip(1L);
        assertEquals(List.of(attraction), attractionList);
    }

    @Test
    void getAll() {
        given(attractionRepository.findAll()).willReturn(List.of(attraction));
        List<Attraction> attractionList = attractionService.getAll();
        assertEquals(attractionList.size(),1);
    }

    @Test
    void getAttractionByNameAttraction() {
        given(attractionRepository.findByNameAttraction("Przewodnik")).willReturn(Optional.ofNullable(attraction));
        Optional<Attraction> attractionOptional = attractionService.getAttractionByNameAttraction("Przewodnik");
        assertEquals(Optional.ofNullable(attraction), attractionOptional);

    }

    @Test
    void getByOwnOffers_idOwnOffer() {
        OwnOffer offer = new OwnOffer();
        offer.setIdOwnOffer(1L);

        given(attractionRepository.findByOwnOffers_idOwnOffer(1L)).willReturn(List.of(attraction));
        List<Attraction> attractionList = attractionService.getByOwnOffers_idOwnOffer(1L);
        assertEquals(List.of(attraction), attractionList);
    }
}