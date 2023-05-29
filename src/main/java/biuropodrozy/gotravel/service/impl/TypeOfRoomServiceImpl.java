package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.TypeOfRoom;
import biuropodrozy.gotravel.repository.TypeOfRoomRepository;
import biuropodrozy.gotravel.service.TypeOfRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TypeOfRoomServiceImpl implements TypeOfRoomService {

    private final TypeOfRoomRepository typeOfRoomRepository;

    @Override
    public List<TypeOfRoom> getAllTypeOfRoom() {
        return typeOfRoomRepository.findAll();
    }

    @Override
    public TypeOfRoom getTypeOfRoom(int idTypeOfRoom) {
        return typeOfRoomRepository.findByIdTypeOfRoom(idTypeOfRoom);
    }

    @Override
    public TypeOfRoom getTypeOfRoomByType(String typeOfRoom) {
        return typeOfRoomRepository.findByType(typeOfRoom);
    }
}
