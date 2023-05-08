package biuropodrozy.gotravel.Model;/*
 * @project gotravel
 * @author kola
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "typesOfRooms")
public class TypeOfRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypeOfRoom;

    private String type;

    private double roomPrice;

    @OneToMany(mappedBy = "typeOfRoom")
    private Set<ReservationsTypeOfRoom> typeOfRooms;
}
