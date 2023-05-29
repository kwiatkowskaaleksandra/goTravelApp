package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.Accommodation;
import biuropodrozy.gotravel.repository.AccommodationRepository;
import biuropodrozy.gotravel.service.AccommodationService;
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
