package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.repository.TypeOfRoomRepository;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Type of room service implementation.
 */
@RequiredArgsConstructor
@Service
public class TypeOfRoomServiceImpl implements TypeOfRoomService {

    private final TypeOfRoomRepository typeOfRoomRepository;

    /**
     * Get all type of rooms.
     *
     * @return list type of rooms.
     */
    @Override
    public List<TypeOfRoom> getAllTypeOfRoom() {
        return typeOfRoomRepository.findAll();
    }

    /**
     * Get type of room by id type of room.
     *
     * @param idTypeOfRoom the id type of room
     * @return the type of room
     */
    @Override
    public TypeOfRoom getTypeOfRoom(int idTypeOfRoom) {
        return typeOfRoomRepository.findByIdTypeOfRoom(idTypeOfRoom);
    }

    /**
     * Get type of rooms by type.
     *
     * @param typeOfRoom the type of room
     * @return the type of room
     */
    @Override
    public TypeOfRoom getTypeOfRoomByType(String typeOfRoom) {
        return typeOfRoomRepository.findByType(typeOfRoom);
    }
}
