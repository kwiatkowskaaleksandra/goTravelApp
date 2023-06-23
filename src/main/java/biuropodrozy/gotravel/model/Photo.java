package biuropodrozy.gotravel.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Photo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photos")
public class Photo {

    /**
     * The unique identifier for the photo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhoto;

    /**
     * The URL of the photo.
     */
    private String urlPhoto;

    /**
     * The trip associated with the photo.
     */
    @ManyToOne
    @JoinColumn(name = "idTrip")
    private Trip trip;

}
