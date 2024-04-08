package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a favorite trip entity, which establishes a many-to-one relationship between users and trips.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favoriteTrips")
public class FavoriteTrips {

    /**
     * Unique identifier for a favorite trip.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFavoriteTrip;

    /**
     * The user who favorited the trip. Establishes a many-to-one relationship with the User entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private User user;

    /**
     * The trip that is favorited. Establishes a many-to-one relationship with the Trip entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTrip")
    private Trip trip;
}
