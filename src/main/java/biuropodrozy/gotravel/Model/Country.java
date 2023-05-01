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
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCountry;

    private String nameCountry;

    @OneToMany(mappedBy = "country")
    private Set<City> cities;
}
