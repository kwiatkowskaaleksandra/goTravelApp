package biuropodrozy.gotravel.service.impl;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.UserRepository;
import biuropodrozy.gotravel.rest.dto.SignUpRequest;
import biuropodrozy.gotravel.security.TokenProvider;
import biuropodrozy.gotravel.security.WebSecurityConfig;
import biuropodrozy.gotravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("Nie ma użytkownika o nazwie: %s", username))
                );
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long idUser) {
        return userRepository.findUserById(idUser);
    }

}
