package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.TypeOfRoom;

import java.util.List;

public interface TypeOfRoomService {

    List<TypeOfRoom> getAllTypeOfRoom();

    TypeOfRoom getTypeOfRoom(int idTypeOfRoom);

    TypeOfRoom getTypeOfRoomByType(String typeOfRoom);

}
