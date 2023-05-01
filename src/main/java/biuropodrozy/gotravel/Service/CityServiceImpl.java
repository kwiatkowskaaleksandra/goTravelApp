package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.City;
import biuropodrozy.gotravel.Model.Country;
import biuropodrozy.gotravel.Repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService{

    private final CityRepository cityRepository;

    @Override
    public Optional<City> getCitiesByCountry(Optional<Country> idCountry) {
        return cityRepository.findByCountry(idCountry);
    }

    @Override
    public Optional<City> getCitiesById(int idCity) {
        return cityRepository.findByIdCity(idCity);
    }
}
