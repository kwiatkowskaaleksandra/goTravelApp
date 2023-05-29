package biuropodrozy.gotravel.service;/*
 * @project gotravel
 * @author kola
 */


import biuropodrozy.gotravel.model.User;

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
