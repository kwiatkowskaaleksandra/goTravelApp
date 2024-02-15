package biuropodrozy.gotravel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


/**
 * The type Own offer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ownOffer")
public class OwnOffer {

    /**
     * The unique identifier for the own offer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOwnOffer;

    /**
     * The date of the reservation.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfReservation;

    /**
     * The number of children included in the offer.
     */
    private int numberOfChildren;

    /**
     * The number of adults included in the offer.
     */
    private int numberOfAdults;

    /**
     * The departure date of the offer.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate departureDate;

    /**
     * The total price of the offer.
     */
    private double totalPrice;

    /**
     * The type of food included in the offer.
     */
    private boolean food;

    /**
     * The number of days for the offer.
     */
    private int numberOfDays;

    /**
     * Represents the payment status.
     */
    private boolean payment;

    /**
     * The set of room types included in the offer.
     */
    @OneToMany(mappedBy = "ownOffer")
    private Set<OwnOfferTypeOfRoom> ownOfferTypeOfRooms;

    /**
     * The city associated with the offer.
     */
    @ManyToOne
    @JoinColumn(name = "idCity")
    private City offerCity;

    /**
     * The accommodation associated with the offer.
     */
    @ManyToOne
    @JoinColumn(name = "idAccommodation")
    private Accommodation offerAccommodation;

    /**
     * The user who created the offer.
     */
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    /**
     * The set of attractions included in the offer.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "attractions_ownTrip", joinColumns = {@JoinColumn(name = "idOwnOffer")}, inverseJoinColumns = {@JoinColumn(name = "idAttraction")})
    private Set<Attraction> offerAttraction;
}
