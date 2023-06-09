package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.Photo;

import java.util.List;

/**
 * The interface Photo service.
 */
public interface PhotoService {

    /**
     * Get all by id trip.
     *
     * @param idTrip the id trip
     * @return list of photos
     */
    List<Photo> getPhotosByIdTrip(Long idTrip);

}
