package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> getPhotosByIdTrip(Long idTrip);

}
