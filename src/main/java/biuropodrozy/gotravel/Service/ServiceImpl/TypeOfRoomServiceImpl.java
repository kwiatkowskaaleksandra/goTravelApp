package biuropodrozy.gotravel.Service.ServiceImpl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.TypeOfRoom;
import biuropodrozy.gotravel.Repository.TypeOfRoomRepository;
import biuropodrozy.gotravel.Service.TypeOfRoomService;
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
}
