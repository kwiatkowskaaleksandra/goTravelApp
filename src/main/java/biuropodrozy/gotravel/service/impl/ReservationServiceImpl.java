package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.repository.ReservationRepository;
import biuropodrozy.gotravel.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Reservations service implementation.
 */
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Save reservation.
     *
     * @param reservation the reservation
     * @return the reservation
     */
    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /**
     * Get reservation by id reservation.
     *
     * @param idReservation the id reservation
     * @return the reservation
     */
    @Override
    public Reservation getReservationsByIdReservation(Long idReservation) {
        return reservationRepository.findReservationsByIdReservation(idReservation);
    }

    /**
     * Get top by descending order by id reservation.
     *
     * @return the reservation
     */
    @Override
    public Reservation getTopByOrderByIdReservation() {
        return reservationRepository.findTopByOrderByIdReservationDesc();
    }

    /**
     * Get reservation by id user.
     *
     * @param idUser the id user
     * @return list of reservation
     */
    @Override
    public List<Reservation> getReservationByIdUser(Long idUser) {
        return reservationRepository.findReservationsByUser_Id(idUser);
    }

    /**
     * Delete reservation.
     *
     * @param reservation the reservation
     */
    @Override
    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
