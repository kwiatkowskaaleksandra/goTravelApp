package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.City;
import biuropodrozy.gotravel.repository.CityRepository;
import biuropodrozy.gotravel.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The City service implementation.
 */
@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    /**
     * Get cities by id country.
     *
     * @param idCountry the id country
     * @return list of cities
     */
    @Override
    public List<City> getCitiesByCountry_IdCountry(int idCountry) {
        return cityRepository.findByCountry_IdCountry(idCountry);
    }

}
