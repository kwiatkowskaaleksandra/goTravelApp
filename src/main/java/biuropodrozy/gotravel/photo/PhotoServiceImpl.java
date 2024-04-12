package biuropodrozy.gotravel.photo;

import biuropodrozy.gotravel.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link PhotoService} interface.
 */
@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

    /**
     * Repository interface for managing photos.
     */
    private final PhotoRepository photoRepository;

    @Override
    public List<Photo> getPhotosByIdTrip(final Long idTrip) {
        return photoRepository.findAllByTrip_IdTrip(idTrip);
    }

    @Override
    public void savePhoto(Photo photo) {
        photoRepository.save(photo);
    }

    @Override
    public void deletePhotoForTrip(Trip trip) {
        List<Photo> allPhotosForTrip = getPhotosByIdTrip(trip.getIdTrip());
        photoRepository.deleteAll(allPhotosForTrip);
    }

}
