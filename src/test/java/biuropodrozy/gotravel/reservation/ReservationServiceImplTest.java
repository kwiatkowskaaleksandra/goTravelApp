package biuropodrozy.gotravel.reservation;

import biuropodrozy.gotravel.accommodation.Accommodation;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.insurance.Insurance;
import biuropodrozy.gotravel.invoice.InvoiceGenerator;
import biuropodrozy.gotravel.mail.MailService;
import biuropodrozy.gotravel.mail.TemplateDataStrategy;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoom;
import biuropodrozy.gotravel.reservationTypeOfRoom.ReservationsTypeOfRoomService;
import biuropodrozy.gotravel.trip.Trip;
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
class ReservationServiceImplTest {

    private ReservationServiceImpl reservationService;
    @Mock private ReservationRepository reservationRepository;
    @Mock private TypeOfRoomService typeOfRoomService;
    @Mock private ValidateOfThePurchasedOfferServiceImpl validateReservation;
    @Mock private ReservationsTypeOfRoomService reservationsTypeOfRoomService;
    @Mock private MailService mailService;
    @Mock private InvoiceGenerator invoiceGenerator;
    @Mock private @Qualifier("reservationConfirmation") TemplateDataStrategy templateDataStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationServiceImpl(reservationRepository, typeOfRoomService, reservationsTypeOfRoomService, validateReservation, mailService,
                invoiceGenerator, templateDataStrategy);
    }

    @Test
    void saveReservation_success() {
        User user = new User();
        TypeOfRoom typeOfRoom = new TypeOfRoom();
        Accommodation accommodation = new Accommodation();
        City city = new City();
        ReservationsTypeOfRoom reservationsTypeOfRoom = new ReservationsTypeOfRoom();
        reservationsTypeOfRoom.setTypeOfRoom(typeOfRoom);
        Trip trip = new Trip();
        trip.setTripAccommodation(accommodation);
        trip.setTripCity(city);
        Insurance insurance = new Insurance();
        Reservation reservation = new Reservation(1L, LocalDate.now(), 2, 4, LocalDate.now(), 0,
                true, true, true, Set.of(reservationsTypeOfRoom), user, trip, insurance);
        Long expectedId = 1L;

        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation saved = invocation.getArgument(0);
            saved.setIdReservation(expectedId);
            return saved;
        });

        long actualId = reservationService.saveReservation(reservation, user);

        assertEquals(expectedId, actualId);
        verify(reservationRepository, times(1)).save(reservation);
        verify(mailService).sendMail(eq(user.getEmail()), anyString(), anyString(), any());
        verify(validateReservation).validateReservationData(any(ValidationData.class));
        verify(reservationsTypeOfRoomService, times(reservation.getTypeOfRoomReservation().size())).saveReservationsTypeOfRoom(any());
    }

    @Test
    void updatePaymentStatus() {
        long idReservation = 1L;
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);

        when(reservationRepository.findReservationsByIdReservation(idReservation)).thenReturn(reservation);
        reservationService.updatePaymentStatus(idReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void getReservationActiveOrders() {
        User user = new User();
        String period = "activeOrders";
        Reservation reservation1 = new Reservation();
        reservation1.setIdReservation(1L);
        reservation1.setDepartureDate(LocalDate.of(2025, Month.APRIL, 12));
        Reservation reservation2 = new Reservation();
        reservation2.setIdReservation(2L);
        reservation2.setDepartureDate(LocalDate.of(2025, Month.NOVEMBER, 12));

        List<Reservation> reservations = new ArrayList<>(List.of(reservation1, reservation2));

        when(reservationRepository.findFutureDeparturesForUser(user)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationActiveOrders(user, period);

        assertEquals(2, result.size());
        assertEquals(reservation1, result.get(0));
    }

    @Test
    void getReservationPastOrders() {
        User user = new User();
        String period = "pastOrders";
        Reservation reservation1 = new Reservation();
        reservation1.setIdReservation(1L);
        reservation1.setDepartureDate(LocalDate.of(2023, Month.APRIL, 12));
        Reservation reservation2 = new Reservation();
        reservation2.setIdReservation(2L);
        reservation2.setDepartureDate(LocalDate.of(2023, Month.NOVEMBER, 12));

        List<Reservation> reservations = new ArrayList<>(List.of(reservation1, reservation2));

        when(reservationRepository.findPastDeparturesForUser(user)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationActiveOrders(user, period);

        assertEquals(2, result.size());
        assertEquals(reservation1, result.get(0));
    }

    @Test
    void deleteReservation() {
        Long idReservation = 1L;
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);
        ReservationsTypeOfRoom reservationsTypeOfRoom = new ReservationsTypeOfRoom();
        reservationsTypeOfRoom.setIdReservationsTypeOfRoom(1);
        reservationsTypeOfRoom.setReservation(reservation);

        when(reservationRepository.findReservationsByIdReservation(idReservation)).thenReturn(reservation);

        List<ReservationsTypeOfRoom> typeOfRooms = new ArrayList<>(List.of(reservationsTypeOfRoom));
        when(reservationsTypeOfRoomService.findByReservation_IdReservation(reservation.getIdReservation())).thenReturn(typeOfRooms);

        reservationService.deleteReservation(idReservation);

        verify(reservationRepository, times(1)).findReservationsByIdReservation(idReservation);
        verify(reservationRepository, times(1)).delete(reservation);
        typeOfRooms.forEach(room -> verify(reservationsTypeOfRoomService).deleteReservationsTypeOfRoom(room));
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void getReservationInvoice() {
        Long idReservation = 1L;
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);
        byte[] expectedInvoice = new byte[] {1, 2, 3};

        when(reservationRepository.findReservationsByIdReservation(idReservation)).thenReturn(reservation);
        when(invoiceGenerator.generateInvoice(reservation)).thenReturn(expectedInvoice);

        byte[] actualInvoice = reservationService.getReservationInvoice(idReservation);

        assertNotNull(actualInvoice);
        verify(reservationRepository, times(1)).findReservationsByIdReservation(idReservation);
        verify(invoiceGenerator, times(1)).generateInvoice(reservation);
    }

    @Test
    void getAllActiveReservationNotAccepted() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setDepartureDate(LocalDate.of(2025, Month.DECEMBER, 1));
        reservation.setAccepted(false);
        reservation.setChangedAcceptanceState(false);
        Pageable pageable = PageRequest.of(0, 1);

        List<Reservation> allOffers = new ArrayList<>(List.of(reservation));

        when(reservationRepository.findAllFutureDeparturesNotAccepted()).thenReturn(allOffers);

        Page<Reservation> result = reservationService.getAllActiveReservationNotAccepted(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(reservation, result.getContent().get(0));
    }

    @Test
    void changeAcceptStatus() {
        String acceptStatus = "accept";
        Long idReservation = 1L;
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);

        when(reservationRepository.findReservationsByIdReservation(idReservation)).thenReturn(reservation);
        reservationService.changeAcceptStatus(idReservation, acceptStatus);
        verify(reservationRepository, times(1)).save(reservation);
    }
}