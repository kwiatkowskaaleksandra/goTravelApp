package biuropodrozy.gotravel.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CountryService} interface.
 */
@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    /**
     * The CountryRepository instance used for accessing and manipulating country data.
     */
    private final CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
