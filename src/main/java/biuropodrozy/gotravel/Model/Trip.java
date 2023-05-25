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
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrip;

    @ManyToOne
    @JoinColumn(name = "idCity")
    private City tripCity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "idTransport")
    private Transport tripTransport;

    @ManyToOne
    @JoinColumn(name = "idAccommodation")
    private Accommodation tripAccommodation;

    private String food;

    private String typeOfTrip;

    private int numberOfDays;

    @OneToMany(mappedBy = "trip")
    private Set<Photo> tripPhoto;

    private String representativePhoto;

    @Column(columnDefinition = "LONGTEXT")
    private String tripDescription;

    @OneToMany(mappedBy = "trip")
    private Set<Opinion> tripOpinion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "attractions_trips", joinColumns = {@JoinColumn(name = "idTrip")}, inverseJoinColumns = {@JoinColumn(name = "idAttraction")})
    private Set<Attraction> tripAttraction;

    @OneToMany(mappedBy = "trip")
    private Set<Reservation> reservations;

}
