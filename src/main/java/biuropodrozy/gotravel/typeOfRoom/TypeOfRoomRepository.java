package biuropodrozy.gotravel.typeOfRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Type od room repository.
 */
@Repository
public interface TypeOfRoomRepository extends JpaRepository<TypeOfRoom, Integer> {

    /**
     * Find by type.
     *
     * @param type the type of room
     * @return the type of room
     */
    TypeOfRoom findByType(String type);
}
