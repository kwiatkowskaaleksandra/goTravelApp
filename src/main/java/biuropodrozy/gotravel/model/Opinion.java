package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The type Opinion.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "opinions")
public class Opinion {

    /**
     * The unique identifier for the opinion.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOpinion;

    /**
     * The description of the opinion.
     */
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    /**
     * The number of stars given in the opinion.
     */
    private double stars;

    /**
     * The date when the opinion was given.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfAddingTheOpinion;

    /**
     * The user who provided the opinion.
     */
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    /**
     * The trip associated with the opinion.
     */
    @ManyToOne
    @JoinColumn(name = "idTrip")
    private Trip trip;
}
