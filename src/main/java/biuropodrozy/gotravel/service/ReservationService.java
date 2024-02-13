package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.User;

import java.util.List;

/**
 * The interface Reservations service.
 */
public interface ReservationService {

    /**
     * Save reservation.
     *
     * @param reservation the reservation
     */
    void saveReservation(Reservation reservation, User user);

    /**
     * Get reservation by id reservation.
     *
     * @param idReservation the id reservation
     * @return the reservation
     */
    Reservation getReservationsByIdReservation(Long idReservation);

    /**
     * Get top by descending order by id reservation.
     *
     * @return the reservation
     */
    Reservation getTopByOrderByIdReservation();

    /**
     * Get reservation by id user.
     *
     * @param idUser the id user
     * @return list of reservation
     */
    List<Reservation> getReservationByIdUser(Long idUser);

    /**
     * Delete reservation.
     *
     * @param reservation the reservation
     */
    void deleteReservation(Reservation reservation);

}
