package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.TypeOfRoom;

import java.util.List;

public interface TypeOfRoomService {

    List<TypeOfRoom> getAllTypeOfRoom();

    TypeOfRoom getTypeOfRoom(int idTypeOfRoom);

}
