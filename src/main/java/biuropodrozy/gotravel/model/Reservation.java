package biuropodrozy.gotravel.model;/*
 * @project gotravel
 * @author kola
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Temporal(TemporalType.DATE)
    private Date dateOfReservation;

    private int numberOfChildren;

    private int numberOfAdults;

    @Temporal(TemporalType.DATE)
    private LocalDate departureDate;

    private double totalPrice;

    @OneToMany(mappedBy = "reservation")
    private Set<ReservationsTypeOfRoom> typeOfRoomReservation;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idTrip")
    private Trip trip;
}
