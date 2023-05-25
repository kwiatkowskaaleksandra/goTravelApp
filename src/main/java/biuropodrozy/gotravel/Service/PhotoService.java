package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> getPhotosByIdTrip(Long idTrip);

}
