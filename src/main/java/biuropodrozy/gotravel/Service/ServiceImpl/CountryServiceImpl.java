package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Country;
import biuropodrozy.gotravel.Repository.CountryRepository;
import biuropodrozy.gotravel.Service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Optional<Country> getCountryById(int idCountry) {
        return countryRepository.findByIdCountry(idCountry);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
