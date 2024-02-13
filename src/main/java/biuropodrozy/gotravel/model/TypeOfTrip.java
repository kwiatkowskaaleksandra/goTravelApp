package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "typeOfTrip")
public class TypeOfTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypeOfTrip;

    private String name;

    @OneToMany(mappedBy = "typeOfTrip")
    private Set<Trip> trips;
}
