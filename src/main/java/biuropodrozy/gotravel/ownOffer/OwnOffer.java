package biuropodrozy.gotravel.ownOffer;

import biuropodrozy.gotravel.accommodation.Accommodation;
import biuropodrozy.gotravel.attraction.Attraction;
import biuropodrozy.gotravel.city.City;
import biuropodrozy.gotravel.insurance.Insurance;
import biuropodrozy.gotravel.ownOfferTypeOfRoom.OwnOfferTypeOfRoom;
import biuropodrozy.gotravel.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


/**
 * The type Own offer.
 */
@Getter
@Setter
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
    @OneToMany(mappedBy = "ownOffer", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"ownOffer"})
    private Set<OwnOfferTypeOfRoom> ownOfferTypeOfRooms;

    /**
     * The city associated with the offer.
     */
    @ManyToOne
    @JoinColumn(name = "idCity")
    @JsonIgnoreProperties({"ownOffers", "trips"})
    private City offerCity;

    /**
     * The accommodation associated with the offer.
     */
    @ManyToOne
    @JoinColumn(name = "idAccommodation")
    @JsonIgnoreProperties({"ownOffers", "trips"})
    private Accommodation offerAccommodation;

    /**
     * The user who created the offer.
     */
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private User user;

    /**
     * Status of acceptance of the reservation by the employee.
     */
    private boolean accepted;

    /**
     * Acceptance status change status.
     */
    private boolean changedAcceptanceState;

    /**
     * The set of attractions included in the offer.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "attractions_ownTrip", joinColumns = {@JoinColumn(name = "idOwnOffer")}, inverseJoinColumns = {@JoinColumn(name = "idAttraction")})
    private Set<Attraction> offerAttraction;

    /**
     * The insurance associated with this own offer.
     */
    @ManyToOne
    @JoinColumn(name = "idInsurance")
    @JsonIgnoreProperties({"ownOffers", "reservations"})
    private Insurance insuranceOwnOffer;
}
