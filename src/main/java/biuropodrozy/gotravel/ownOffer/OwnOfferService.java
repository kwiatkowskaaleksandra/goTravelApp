package biuropodrozy.gotravel.ownOffer;

import biuropodrozy.gotravel.exception.ReservationException;
import biuropodrozy.gotravel.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Own offer service.
 */
public interface OwnOfferService {

    /**
     * Saves the provided own offer after validating reservation data.
     *
     * @param ownOffer The own offer to be saved.
     * @param user     The user associated with the own offer.
     * @throws ReservationException if the total price of the own offer is less than or equal to zero.
     * @return The id of the booked trip.
     */
    long saveOwnOffer(OwnOffer ownOffer, User user);

    /**
     * Calculates the total price for the given own offer.
     *
     * @param ownOffer The own offer for which the total price needs to be calculated.
     * @param user     The user associated with the own offer.
     * @return The total price of the own offer.
     */
    double getTotalPrice(OwnOffer ownOffer, User user);

    /**
     * Updates the payment status of the own offer with the specified ID.
     * This method retrieves the own offer from the repository using the provided ID,
     * sets the payment status to true, and saves the updated own offer back to the repository.
     *
     * @param idOwnOffer The ID of the own offer whose payment status is to be updated.
     */
    void updatePaymentStatus(long idOwnOffer);

    /**
     * Retrieves a list of active orders (future departures) or past orders (departures that have already occurred)
     * for the specified user based on the given period.
     *
     * @param user   The user for whom to retrieve the active or past orders.
     * @param period The period indicating whether to retrieve active orders ("activeOrders") or past orders ("purchasedTrips").
     * @return A list of own offers representing the active or past orders.
     */
    List<OwnOffer> getOwnOffersActiveOrders(User user, String period);

    /**
     * Deletes the own offer with the specified ID, along with associated type of rooms and attractions.
     *
     * @param idOwnOffer The ID of the own offer to be deleted.
     */
    void deleteOwnOffer(Long idOwnOffer);

    /**
     * Generates an invoice for the own offer with the specified ID.
     *
     * @param idOwnOffer The ID of the own offer for which the invoice is to be generated.
     * @return The byte array representing the generated invoice.
     */
    byte[] getReservationInvoice(Long idOwnOffer);

    /**
     * Retrieves a page of active own offers that have not been accepted.
     *
     * @param pageable the pagination information
     * @return a page of active own offers not accepted
     */
    Page<OwnOffer> getAllActiveOwnOffersNotAccepted(Pageable pageable);

    /**
     * Changes the acceptance status of an own offer.
     *
     * @param idOwnOffer    the ID of the own offer
     * @param acceptStatus  the new acceptance status
     */
    void changeAcceptStatus(Long idOwnOffer, String acceptStatus);
}
