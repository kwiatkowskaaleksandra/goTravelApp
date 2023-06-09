package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Country;
import biuropodrozy.gotravel.repository.CountryRepository;
import biuropodrozy.gotravel.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Country service implementation.
 */
@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    /**
     * Getl all countries.
     *
     * @return list of countries.
     */
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
