package biuropodrozy.gotravel.typeOfTrip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfTripRepository extends JpaRepository<TypeOfTrip, Long> {

}
