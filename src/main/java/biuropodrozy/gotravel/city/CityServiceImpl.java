package biuropodrozy.gotravel.city;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CityService} interface.
 */
@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    /**
     * The CityRepository instance used for accessing and manipulating city data.
     */
    private final CityRepository cityRepository;

    @Override
    public List<City> getCitiesByCountry_IdCountry(final int idCountry) {
        return cityRepository.findByCountry_IdCountry(idCountry);
    }

}
