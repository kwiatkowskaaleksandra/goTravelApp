package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation saveReservation(Reservation reservation);

    Reservation getReservationsByIdReservation(Long idReservation);

    Reservation getTopByOrderByIdReservation();

    List<Reservation> getReservationByIdUser(Long idUser);

    void deleteReservation(Reservation reservation);

}
