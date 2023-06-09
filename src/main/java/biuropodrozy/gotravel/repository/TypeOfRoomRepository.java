package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.TypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Type od room repository.
 */
@Repository
public interface TypeOfRoomRepository extends JpaRepository<TypeOfRoom, Integer> {

    /**
     * Find all room types.
     *
     * @return list of room types
     */
    List<TypeOfRoom> findAll();

    /**
     * Find by id room type.
     *
     * @param idTypeOfRoom the id type of room
     * @return the type of room
     */
    TypeOfRoom findByIdTypeOfRoom(int idTypeOfRoom);

    /**
     * Finf by type.
     *
     * @param type the type
     * @return the type of room
     */
    TypeOfRoom findByType(String type);
}
