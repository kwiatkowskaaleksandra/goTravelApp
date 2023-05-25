package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.Accommodation;
import biuropodrozy.gotravel.Repository.AccommodationRepository;
import biuropodrozy.gotravel.Service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Override
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }
}
