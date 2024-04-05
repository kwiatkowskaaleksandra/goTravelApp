package biuropodrozy.gotravel.repository;

import biuropodrozy.gotravel.model.Role;
import biuropodrozy.gotravel.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing user roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to search for
     * @return an Optional containing the role, if found
     */
    Optional<Role> findByName(RoleEnum name);
}
