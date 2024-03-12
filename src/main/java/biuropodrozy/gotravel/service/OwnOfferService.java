package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.User;

import java.util.List;

/**
 * The interface Own offer service.
 */
public interface OwnOfferService {

    /**
     * Saves the provided own offer after validating reservation data.
     *
     * @param ownOffer the own offer
     * @param user the user
     * @return the id of the booked trip
     */
    long saveOwnOffer(OwnOffer ownOffer, User user);

    /**
     * Calculates the total price for the given own offer.
     *
     * @param ownOffer the own offer
     * @param user the user
     * @return the total price
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
     * Retrieves a list of own offers with active orders for the specified user and period.
     *
     * @param user The user for whom to retrieve own offers
     * @param period The period for which to retrieve own offers (e.g., "future", "past")
     * @return A list of own offers with active orders
     */
    List<OwnOffer> getOwnOffersActiveOrders(User user, String period);

    /**
     * Deletes the own offer with the specified ID.
     *
     * @param idOwnOffer The ID of the own offer to be deleted
     */
    void deleteOwnOffer(Long idOwnOffer);

    /**
     * Retrieves the invoice PDF for the own offer with the specified ID.
     *
     * @param idOwnOffer The ID of the own offer for which to retrieve the invoice
     * @return The byte array containing the PDF invoice
     */
    byte[] getReservationInvoice(Long idOwnOffer);
}
