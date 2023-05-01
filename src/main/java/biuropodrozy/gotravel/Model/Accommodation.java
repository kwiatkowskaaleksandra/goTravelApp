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
@Table(name = "accommodation")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccommodation;

    private String nameAccommodation;

    private double priceAccommodation;

    @OneToMany(mappedBy = "idTrip")
    private Set<Trip> trips;
}
