package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Photo;
import biuropodrozy.gotravel.Repository.PhotoRepository;
import biuropodrozy.gotravel.Service.PhotoService;
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
