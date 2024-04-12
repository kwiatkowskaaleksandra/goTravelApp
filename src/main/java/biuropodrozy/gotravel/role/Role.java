package biuropodrozy.gotravel.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing user roles.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
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
