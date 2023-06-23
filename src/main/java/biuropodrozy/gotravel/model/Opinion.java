package biuropodrozy.gotravel.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
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
    private String description;

    /**
     * The date when the opinion was given.
     */
    private LocalDate date;

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
