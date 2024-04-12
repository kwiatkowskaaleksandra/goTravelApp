package biuropodrozy.gotravel.typeOfRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TypeOfRoomService} interface.
 */
@RequiredArgsConstructor
@Service
public class TypeOfRoomServiceImpl implements TypeOfRoomService {

    /**
     * Repository for accessing and managing TypeOfRoom entities.
     */
    private final TypeOfRoomRepository typeOfRoomRepository;

    @Override
    public List<TypeOfRoom> getAllTypeOfRoom() {
        return typeOfRoomRepository.findAll();
    }

    @Override
    public TypeOfRoom getTypeOfRoomByType(final String typeOfRoom) {
        return typeOfRoomRepository.findByType(typeOfRoom);
    }
}
