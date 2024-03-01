package biuropodrozy.gotravel.service.mail;

import biuropodrozy.gotravel.model.OwnOffer;
import biuropodrozy.gotravel.model.Reservation;
import biuropodrozy.gotravel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the TemplateDataStrategy interface for preparing template data regarding booking confirmation.
 */
@Service("reservationConfirmation")
@Slf4j
public class ReservationConfirmationService implements TemplateDataStrategy {

    @Override
    public Map<String, Object> prepareTemplateData(User user, Object context) {
        if (context instanceof Reservation) {
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("username", user.getUsername());
            templateData.put("departureDate", ((Reservation) context).getDepartureDate());
            templateData.put("numberOfDays", ((Reservation) context).getTrip().getNumberOfDays());
            templateData.put("price", ((Reservation) context).getTotalPrice());
            return templateData;
        } else if (context instanceof OwnOffer) {
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("username", user.getUsername());
            templateData.put("departureDate", ((OwnOffer) context).getDepartureDate());
            templateData.put("numberOfDays", ((OwnOffer) context).getNumberOfDays());
            templateData.put("price", ((OwnOffer) context).getTotalPrice());
            return templateData;
        } else {
            throw new IllegalArgumentException("Unsupported context type");
        }
    }
}
