package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Photo;
import biuropodrozy.gotravel.repository.PhotoRepository;
import biuropodrozy.gotravel.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Photo service implementation.
 */
@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    /**
     * Get all by id trip.
     *
     * @param idTrip the id trip
     * @return list of photos
     */
    @Override
    public List<Photo> getPhotosByIdTrip(Long idTrip) {
        return photoRepository.findAllByTrip_IdTrip(idTrip);
    }

}
