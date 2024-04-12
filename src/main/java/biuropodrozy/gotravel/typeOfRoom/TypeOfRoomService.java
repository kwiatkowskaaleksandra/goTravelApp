package biuropodrozy.gotravel.typeOfRoom;

import java.util.List;

/**
 * The interface Type of room service.
 */
public interface TypeOfRoomService {

    /**
     * Get all type of rooms.
     *
     * @return list type of rooms.
     */
    List<TypeOfRoom> getAllTypeOfRoom();

    /**
     * Get type of rooms by type.
     *
     * @param typeOfRoom the type of room
     * @return the type of room
     */
    TypeOfRoom getTypeOfRoomByType(String typeOfRoom);

}
