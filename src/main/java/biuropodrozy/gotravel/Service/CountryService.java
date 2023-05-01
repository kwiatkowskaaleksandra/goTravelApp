package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    Optional<Country> getCountryById(int idCountry);

    List<Country> getAllCountries();

}
