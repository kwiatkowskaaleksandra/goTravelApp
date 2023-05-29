package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Photo;
import biuropodrozy.gotravel.repository.PhotoRepository;
import biuropodrozy.gotravel.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    @Override
    public List<Photo> getPhotosByIdTrip(Long idTrip) {
        return photoRepository.findAllByTrip_IdTrip(idTrip);
    }

}
