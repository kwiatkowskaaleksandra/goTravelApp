package biuropodrozy.gotravel.Repository;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.TypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfRoomRepository extends JpaRepository<TypeOfRoom, Integer> {

    List<TypeOfRoom> findAll();

    TypeOfRoom findByIdTypeOfRoom(int idTypeOfRoom);

    TypeOfRoom findByType(String type);
}
