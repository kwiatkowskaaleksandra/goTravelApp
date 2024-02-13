package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.TypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Type od room repository.
 */
@Repository
public interface TypeOfRoomRepository extends JpaRepository<TypeOfRoom, Integer> {

    /**
     * Finf by type.
     *
     * @param type the type
     * @return the type of room
     */
    TypeOfRoom findByType(String type);
}
