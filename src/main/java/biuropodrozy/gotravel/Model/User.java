package biuropodrozy.gotravel.Model;/*
 * @project gotravel
 * @author kola
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String role;

    @Size (max = 9, min = 9)
    private int phoneNumber;

    private String city;

    private String street;

    private String streetNumber;

    @Size (max = 5, min = 5)
    private int zipCode;

    @OneToMany(mappedBy = "user")
    private Set<Opinion> opinions;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

}
