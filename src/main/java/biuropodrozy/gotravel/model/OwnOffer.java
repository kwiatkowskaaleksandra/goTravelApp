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
@Table(name = "ownOffer")
public class OwnOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOwnOffer;

    @Temporal(TemporalType.DATE)
    private Date dateOfReservation;

    private int numberOfChildren;

    private int numberOfAdults;

    @Temporal(TemporalType.DATE)
    private LocalDate departureDate;

    private double totalPrice;

    private String food;

    private int numberOfDays;

    @OneToMany(mappedBy = "ownOffer")
    private Set<OwnOfferTypeOfRoom> ownOfferTypeOfRooms;

    @ManyToOne
    @JoinColumn(name = "idCity")
    private City offerCity;

    @ManyToOne
    @JoinColumn(name = "idAccommodation")
    private Accommodation offerAccommodation;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "attractions_ownTrip", joinColumns = {@JoinColumn(name = "idOwnOffer")}, inverseJoinColumns = {@JoinColumn(name = "idAttraction")})
    private Set<Attraction> offerAttraction;
}
