package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Photo;

import java.util.List;
import java.util.Set;

public interface PhotoService {

    List<Photo> getPhotosByIdTrip(Long idTrip);

}
