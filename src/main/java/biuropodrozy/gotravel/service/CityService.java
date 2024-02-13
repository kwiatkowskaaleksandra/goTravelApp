package biuropodrozy.gotravel.service;

import biuropodrozy.gotravel.model.City;

import java.util.List;

/**
 * The interface City service.
 */
public interface CityService {

    /**
     * Get cities by id country.
     *
     * @param idCountry the id country
     * @return list of cities
     */
    List<City> getCitiesByCountry_IdCountry(int idCountry);

}
