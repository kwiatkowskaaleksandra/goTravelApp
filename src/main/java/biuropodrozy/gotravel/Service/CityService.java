package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.City;
import java.util.List;

public interface CityService {

    List<City> getCitiesByCountry_IdCountry(int idCountry);

}
