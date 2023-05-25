package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.TypeOfRoom;

import java.lang.reflect.Type;
import java.util.List;

public interface TypeOfRoomService {

    List<TypeOfRoom> getAllTypeOfRoom();

    TypeOfRoom getTypeOfRoom(int idTypeOfRoom);

    TypeOfRoom getTypeOfRoomByType(String typeOfRoom);

}
