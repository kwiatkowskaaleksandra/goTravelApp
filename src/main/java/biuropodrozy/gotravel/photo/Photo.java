package biuropodrozy.gotravel.photo;

import biuropodrozy.gotravel.trip.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Photo.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
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
    @JsonIgnore
    private Trip trip;

}
