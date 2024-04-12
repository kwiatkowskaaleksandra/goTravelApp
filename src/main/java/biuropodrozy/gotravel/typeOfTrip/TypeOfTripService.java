package biuropodrozy.gotravel.typeOfTrip;

import java.util.List;

/**
 * Service interface for managing types of trips.
 */
public interface TypeOfTripService {

    /**
     * Retrieves all types of trips.
     *
     * @return a list of all types of trips
     */
    List<TypeOfTrip> getAllTypeOfTrips();
}
