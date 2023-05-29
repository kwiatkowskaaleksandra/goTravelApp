package biuropodrozy.gotravel.Service;/*
 * @project gotravel
 * @author kola
 */


import biuropodrozy.gotravel.Model.User;
import biuropodrozy.gotravel.Rest.dto.SignUpRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    void deleteUser(User user);

    User getUserById(Long idUser);


}
