package biuropodrozy.gotravel.ownOffer;

import biuropodrozy.gotravel.accommodation.Accommodation;
import biuropodrozy.gotravel.accommodation.AccommodationService;
import biuropodrozy.gotravel.attraction.Attraction;
import biuropodrozy.gotravel.attraction.AttractionService;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.insurance.Insurance;
import biuropodrozy.gotravel.invoice.InvoiceGenerator;
import biuropodrozy.gotravel.mail.MailService;
import biuropodrozy.gotravel.mail.TemplateDataStrategy;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoom;
import biuropodrozy.gotravel.typeOfRoom.TypeOfRoomService;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.validation.ValidateOfThePurchasedOfferServiceImpl;
import biuropodrozy.gotravel.validation.ValidationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OwnOfferServiceImplTest {

    private OwnOfferServiceImpl ownOfferService;
    @Mock private OwnOfferRepository ownOfferRepository;
    @Mock private AccommodationService accommodationService;
    @Mock private AttractionService attractionService;
    @Mock private TypeOfRoomService typeOfRoomService;
    @Mock private ValidateOfThePurchasedOfferServiceImpl validateReservation;
    @Mock private OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;
    @Mock private MailService mailService;
    @Mock private InvoiceGenerator invoiceGenerator;
    @Mock private @Qualifier("reservationConfirmation") TemplateDataStrategy templateDataStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ownOfferService = new OwnOfferServiceImpl(ownOfferRepository, accommodationService, attractionService, typeOfRoomService,
                validateReservation, ownOfferTypeOfRoomService, mailService, invoiceGenerator, templateDataStrategy);
    }

    @Test
    void saveOwnOffer_success() {
        City city = new City();
        Accommodation accommodation = new Accommodation();
        Attraction attraction = new Attraction();
        TypeOfRoom typeOfRoom = new TypeOfRoom();
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
        ownOfferTypeOfRoom.setTypeOfRoom(typeOfRoom);
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setTotalPrice(1000);
        ownOffer.setOfferCity(city);
        ownOffer.setOfferAccommodation(accommodation);
        ownOffer.setOfferAttraction(Set.of(attraction));
        ownOffer.setOwnOfferTypeOfRooms(Set.of(ownOfferTypeOfRoom));
        User user = new User();
        Long expectedId = 1L;

        when(ownOfferRepository.save(any(OwnOffer.class))).thenAnswer(invocation -> {
            OwnOffer saved = invocation.getArgument(0);
            saved.setIdOwnOffer(expectedId);
            return saved;
        });

        long actualId = ownOfferService.saveOwnOffer(ownOffer, user);

        assertEquals(expectedId, actualId);
        verify(ownOfferRepository).save(ownOffer);
        verify(mailService).sendMail(eq(user.getEmail()), anyString(), anyString(), any());
        verify(validateReservation).validateReservationData(any(ValidationData.class));
        verify(ownOfferTypeOfRoomService, times(ownOffer.getOwnOfferTypeOfRooms().size())).saveOwnOfferTypeOfRoom(any());
    }

    @Test
    void saveOwnOffer_IncorrectPrice_ThrowsException() {
        City city = new City();
        Accommodation accommodation = new Accommodation();
        Attraction attraction = new Attraction();
        TypeOfRoom typeOfRoom = new TypeOfRoom();
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
        ownOfferTypeOfRoom.setTypeOfRoom(typeOfRoom);
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setTotalPrice(0);
        ownOffer.setOfferCity(city);
        ownOffer.setOfferAccommodation(accommodation);
        ownOffer.setOfferAttraction(Set.of(attraction));
        ownOffer.setOwnOfferTypeOfRooms(Set.of(ownOfferTypeOfRoom));
        User user = new User();

        Exception exception = assertThrows(ReservationException.class, () -> ownOfferService.saveOwnOffer(ownOffer, user));

        assertEquals("pleaseTryAgain", exception.getMessage());
        verify(ownOfferRepository, never()).save(any(OwnOffer.class));
        verify(mailService, never()).sendMail(anyString(), anyString(), anyString(), any());
    }

    @Test
    void getTotalPrice() {
        City city = new City();
        Accommodation accommodation = new Accommodation();
        accommodation.setPriceAccommodation(100.00);
        Attraction attraction = new Attraction();
        attraction.setPriceAttraction(50.00);
        TypeOfRoom typeOfRoom = new TypeOfRoom();
        typeOfRoom.setRoomPrice(60.00);
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
        ownOfferTypeOfRoom.setTypeOfRoom(typeOfRoom);
        ownOfferTypeOfRoom.setNumberOfRoom(2);
        Insurance insurance = new Insurance();
        insurance.setPrice(300.0);
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setOfferCity(city);
        ownOffer.setOfferAccommodation(accommodation);
        ownOffer.setOfferAttraction(Set.of(attraction));
        ownOffer.setOwnOfferTypeOfRooms(Set.of(ownOfferTypeOfRoom));
        ownOffer.setNumberOfDays(3);
        ownOffer.setFood(true);
        ownOffer.setNumberOfChildren(2);
        ownOffer.setNumberOfAdults(2);
        ownOffer.setInsuranceOwnOffer(insurance);
        User user = new User();

        when(typeOfRoomService.getTypeOfRoomByType(typeOfRoom.getType())).thenReturn(typeOfRoom);
        when(accommodationService.getAccommodationsById(accommodation.getIdAccommodation())).thenReturn(accommodation);
        when(attractionService.getAttractionById(attraction.getIdAttraction())).thenReturn(attraction);

        double totalPrice = ownOfferService.getTotalPrice(ownOffer, user);

        double expectedPrice = ((100 + 60) * 2 * 3) + 50 + (2 * 3 * 100) + (2 * 3 * 200) + 300;
        assertEquals(expectedPrice, totalPrice, 0.01);
    }

    @Test
    void updatePaymentStatus() {
        long idOwnOffer = 1L;
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setIdOwnOffer(1L);

        when(ownOfferRepository.findByIdOwnOffer(idOwnOffer)).thenReturn(ownOffer);
        ownOfferService.updatePaymentStatus(idOwnOffer);
        verify(ownOfferRepository, times(1)).save(ownOffer);
    }

    @Test
    void getOwnOffersActiveOrders() {
        User user = new User();
        user.setId(1L);
        String period = "activeOrders";
        OwnOffer ownOffer1 = new OwnOffer();
        ownOffer1.setIdOwnOffer(1L);
        ownOffer1.setUser(user);
        ownOffer1.setDepartureDate(LocalDate.of(2025, Month.DECEMBER, 1));
        OwnOffer ownOffer2 = new OwnOffer();
        ownOffer2.setIdOwnOffer(2L);
        ownOffer2.setUser(user);
        ownOffer2.setDepartureDate(LocalDate.of(2025, Month.APRIL, 12));

        List<OwnOffer> ownOffers = new ArrayList<>(List.of(ownOffer1, ownOffer2));

        when(ownOfferRepository.findFutureDeparturesForUser(user)).thenReturn(ownOffers);

        List<OwnOffer> result = ownOfferService.getOwnOffersActiveOrders(user, period);

        assertEquals(2, result.size());
        assertEquals(ownOffer1, result.get(0));

    }

    @Test
    void getOwnOffersPastOrders() {
        User user = new User();
        user.setId(1L);
        String period = "pastOrders";
        OwnOffer ownOffer1 = new OwnOffer();
        ownOffer1.setIdOwnOffer(1L);
        ownOffer1.setUser(user);
        ownOffer1.setDepartureDate(LocalDate.of(2022, Month.JANUARY, 4));
        OwnOffer ownOffer2 = new OwnOffer();
        ownOffer2.setIdOwnOffer(2L);
        ownOffer2.setUser(user);
        ownOffer2.setDepartureDate(LocalDate.of(2022, Month.APRIL, 12));

        List<OwnOffer> ownOffers = new ArrayList<>(List.of(ownOffer1, ownOffer2));

        when(ownOfferRepository.findPastDeparturesForUser(user)).thenReturn(ownOffers);

        List<OwnOffer> result = ownOfferService.getOwnOffersActiveOrders(user, period);

        assertEquals(2, result.size());
        assertEquals(ownOffer1, result.get(0));

    }

    @Test
    void deleteOwnOffer_success() {
        Long idOwnOffer = 1L;
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setIdOwnOffer(1L);
        OwnOfferTypeOfRoom ownOfferTypeOfRoom = new OwnOfferTypeOfRoom();
        ownOfferTypeOfRoom.setIdOwnOfferTypeOfRoom(1);
        ownOfferTypeOfRoom.setOwnOffer(ownOffer);

        when(ownOfferRepository.findByIdOwnOffer(idOwnOffer)).thenReturn(ownOffer);

        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = new ArrayList<>(List.of(ownOfferTypeOfRoom));
        when(ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer())).thenReturn(ownOfferTypeOfRooms);

        ownOfferService.deleteOwnOffer(idOwnOffer);

        verify(ownOfferRepository, times(1)).findByIdOwnOffer(idOwnOffer);
        verify(ownOfferRepository, times(1)).delete(ownOffer);
        ownOfferTypeOfRooms.forEach(ownOfferRoom ->
                verify(ownOfferTypeOfRoomService).deleteOwnOfferTypeOfRoom(ownOfferRoom)
        );
        verify(ownOfferRepository,  times(1)).save(ownOffer);
        verify(ownOfferRepository,  times(1)).delete(ownOffer);
    }

    @Test
    void deleteOwnOffer_notFound() {
        Long idOwnOffer = 5L;
        when(ownOfferRepository.findByIdOwnOffer(idOwnOffer)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> ownOfferService.deleteOwnOffer(idOwnOffer));

        verify(ownOfferRepository).findByIdOwnOffer(idOwnOffer);
        verify(ownOfferTypeOfRoomService, never()).findByOwnOffer_IdOwnOffer(any());
        verify(ownOfferTypeOfRoomService, never()).deleteOwnOfferTypeOfRoom(any());
        verify(ownOfferRepository, never()).save(any(OwnOffer.class));
        verify(ownOfferRepository, never()).delete(any(OwnOffer.class));
    }

    @Test
    void getReservationInvoice() {
        Long idOwnOffer = 1L;
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setIdOwnOffer(1L);
        byte[] expectedInvoice = new byte[] {1, 2, 3};

        when(ownOfferRepository.findByIdOwnOffer(idOwnOffer)).thenReturn(ownOffer);
        when(invoiceGenerator.generateInvoice(ownOffer)).thenReturn(expectedInvoice);

        byte[] actualInvoice = ownOfferService.getReservationInvoice(idOwnOffer);

        assertNotNull(actualInvoice);
        verify(ownOfferRepository, times(1)).findByIdOwnOffer(idOwnOffer);
        verify(invoiceGenerator, times(1)).generateInvoice(ownOffer);
    }

    @Test
    void getAllActiveOwnOffersNotAccepted() {
        OwnOffer ownOffer1 = new OwnOffer();
        ownOffer1.setIdOwnOffer(1L);
        ownOffer1.setDepartureDate(LocalDate.of(2025, Month.DECEMBER, 1));
        ownOffer1.setAccepted(false);
        ownOffer1.setChangedAcceptanceState(false);
        Pageable pageable = PageRequest.of(0, 1);

        List<OwnOffer> allOffers = new ArrayList<>(List.of(ownOffer1));

        when(ownOfferRepository.findAllFutureDeparturesNotAccepted()).thenReturn(allOffers);

        Page<OwnOffer> result = ownOfferService.getAllActiveOwnOffersNotAccepted(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(ownOffer1, result.getContent().get(0));
    }

    @Test
    void changeAcceptStatus() {
        String acceptStatus = "accept";
        Long idOwnOffer = 1L;
        OwnOffer ownOffer = new OwnOffer();
        ownOffer.setIdOwnOffer(1L);

        when(ownOfferRepository.findByIdOwnOffer(idOwnOffer)).thenReturn(ownOffer);
        ownOfferService.changeAcceptStatus(idOwnOffer, acceptStatus);
        verify(ownOfferRepository, times(1)).save(ownOffer);

    }
}