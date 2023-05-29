package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.City;

import java.util.List;

public interface CityService {

    List<City> getCitiesByCountry_IdCountry(int idCountry);

}
