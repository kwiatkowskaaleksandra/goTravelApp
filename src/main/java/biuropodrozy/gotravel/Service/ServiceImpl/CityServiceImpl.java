package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.City;
import biuropodrozy.gotravel.Repository.CityRepository;
import biuropodrozy.gotravel.Service.CityService;
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
