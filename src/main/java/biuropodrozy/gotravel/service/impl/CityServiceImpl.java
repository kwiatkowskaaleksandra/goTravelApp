package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.City;
import biuropodrozy.gotravel.repository.CityRepository;
import biuropodrozy.gotravel.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> getCitiesByCountry_IdCountry(int idCountry) {
        return cityRepository.findByCountry_IdCountry(idCountry);
    }

}
