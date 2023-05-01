package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.City;
import biuropodrozy.gotravel.Model.Country;

import java.util.List;
import java.util.Optional;

public interface CityService {

    Optional<City> getCitiesByCountry(Optional<Country> idCountry );

    Optional<City> getCitiesById(int idCity);
}
