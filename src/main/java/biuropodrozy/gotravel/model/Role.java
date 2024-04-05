package biuropodrozy.gotravel.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing user roles.
 */
@Entity
@Table(name = "roles")
@Data
public class Role {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    /**
     * The name of the role.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum name;

}
