package biuropodrozy.gotravel.photo;

import biuropodrozy.gotravel.trip.Trip;

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

    /**
     * Saves a photo.
     *
     * @param photo the photo to be saved
     */
    void savePhoto(Photo photo);

    /**
     * Deletes all photos associated with a trip.
     *
     * @param trip the trip for which photos will be deleted
     */
    void deletePhotoForTrip(Trip trip);
}
