package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Attraction;
import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttractionControllerTest {

    @Mock
    private AttractionService attractionService;

    @InjectMocks
    private AttractionController attractionController;

    @Test
    void readAllTrips() {
        List<Attraction> attractionList = new ArrayList<>();
        Set<Attraction> set = new HashSet<>();
        Set<Trip> setTrip = new HashSet<>();

        Attraction attraction1 = new Attraction();
        attraction1.setIdAttraction(1);
        attraction1.setNameAttraction("atrakcja");
        attraction1.setPriceAttraction(120.0);


        set.add(attraction1);

        Trip trip = new Trip();
        trip.setIdTrip(1L);
        trip.setPrice(120.0);
        trip.setFood("śniadanie");
        trip.setNumberOfDays(12);
        trip.setTripAttraction(set);

        setTrip.add(trip);
        attraction1.setTrips(setTrip);
        attractionList.add(attraction1);

        when(attractionService.getAllByTrips_idTrip(1L)).thenReturn(attractionList);
        ResponseEntity<List<Attraction>> response = attractionController.readAllTrips(1L);
        HttpStatusCode status = response.getStatusCode();
        List<Attraction> attractions = response.getBody();
        assertEquals(status,  HttpStatusCode.valueOf(200));
        assert attractions != null;
        assertEquals(attractions.size(),  attractionList.size());
    }

    @Test
    void readAllAttraction() {

        List<Attraction> attractionList = new ArrayList<>();

        Attraction attraction1 = new Attraction();
        attraction1.setIdAttraction(1);
        attraction1.setNameAttraction("atrakcja");
        attraction1.setPriceAttraction(120.0);

        Attraction attraction2 = new Attraction();
        attraction2.setIdAttraction(2);
        attraction2.setNameAttraction("atrakcja2");
        attraction2.setPriceAttraction(220.0);

        attractionList.add(attraction1);
        attractionList.add(attraction2);

        when(attractionService.getAll()).thenReturn(attractionList);

        ResponseEntity<List<Attraction>> response = attractionController.readAllAttraction();
        HttpStatusCode status = response.getStatusCode();
        List<Attraction> attractions = response.getBody();

        assertEquals(status,  HttpStatusCode.valueOf(200));
        assert attractions != null;
        assertEquals(attractions.size(),  attractionList.size());
    }
}