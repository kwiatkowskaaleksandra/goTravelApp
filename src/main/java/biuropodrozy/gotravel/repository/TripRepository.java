package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Trip repository.
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    /**
     * Find all trips.
     *
     * @return list of trips
     */
    List<Trip> findAll();

    /**
     * Find by id trip.
     *
     * @param idTrip the id trip
     * @return the trip
     */
    Trip findByIdTrip(Long idTrip);

    /**
     * Find all by type of trip.
     *
     * @param typeOfTrip the type of room
     * @return list of trips
     */
    List<Trip> findAllByTypeOfTrip(String typeOfTrip);
}
