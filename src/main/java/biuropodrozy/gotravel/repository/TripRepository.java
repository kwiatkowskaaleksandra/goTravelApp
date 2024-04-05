package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The interface Trip repository.
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    /**
     * Find by id trip.
     *
     * @param idTrip the id trip
     * @return the trip
     */
    Trip findByIdTrip(Long idTrip);

    /**
     * Retrieves all trips by the name of the type of trip with pagination.
     *
     * @param typeOfTrip The name of the type of trip to filter.
     * @param pageable   Pagination information.
     * @return Page of trips filtered by the name of the type of trip.
     */
    Page<Trip> findAllByTypeOfTrip_Name(String typeOfTrip, Pageable pageable);

    /**
     * Counts the number of trips by the name of the type of trip.
     *
     * @param typeOfTrip The name of the type of trip to count.
     * @return The count of trips filtered by the name of the type of trip.
     */
    int countTripByTypeOfTrip_Name(String typeOfTrip);

    /**
     * Finds the trip with the lowest price.
     *
     * @return the trip with the lowest price
     */
    Trip findFirstByOrderByPriceAsc();

    /**
     * Finds the trip with the highest price.
     *
     * @return the trip with the highest price
     */
    Trip findFirstByOrderByPriceDesc();

    /**
     * Finds the top 5 most booked trips.
     *
     * @param pageable pagination information
     * @return list of objects representing the top 5 most booked trips
     */
    @Query("SELECT r.trip, COUNT(r) as reservationCount FROM Reservation r GROUP BY r.trip ORDER BY reservationCount DESC")
    List<Object[]> findTop5MostBookedTrips(Pageable pageable);
}
