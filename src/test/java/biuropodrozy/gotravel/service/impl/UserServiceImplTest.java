package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.exception.UserNotFoundException;
import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1L);
        user.setFirstname("Jan");
        user.setLastname("Nowak");
        user.setEmail("jan@wp.pl");
        user.setUsername("janek12");
        user.setPassword("janek12");
    }

    @Test
    void getUsers() {
        given(userRepository.findAll()).willReturn(List.of(user));

        List<User> users1 = userService.getUsers();
        assertThat(users1).isNotNull();
        assertEquals(users1.size(), 1);
    }

    @Test
    void getUserByUsername() {
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.ofNullable(user));
        Optional<User> user1 = userService.getUserByUsername(user.getUsername());

        assertThat(user1).isNotNull();
    }

    @Test
    void existUserWithUsername() {
        given(userRepository.existsByUsername(user.getUsername())).willReturn(true);
        boolean usernameExists = userService.hasUserWithUsername("janek12");
        assertTrue(usernameExists);
    }

    @Test
    void noExistUserWithUsername() {
        given(userRepository.existsByUsername("janNowak")).willReturn(false);
        boolean usernameExists = userService.hasUserWithUsername("janNowak");
        assertFalse(usernameExists);
    }

    @Test
    void existUserWithEmail() {
        given(userRepository.existsByEmail(user.getEmail())).willReturn(true);
        boolean emailExists = userService.hasUserWithEmail("jan@wp.pl");
        assertTrue(emailExists);
    }

    @Test
    void noExistUserWithEmail() {
        given(userRepository.existsByEmail("annaNowak@wp.pl")).willReturn(false);
        boolean emailExists = userService.hasUserWithEmail("annaNowak@wp.pl");
        assertFalse(emailExists);
    }

    @Test
    void validateAndGetUserByUsername() {
        assertThrows(UserNotFoundException.class, () ->{
            User user1 = userService.validateAndGetUserByUsername("marek");
            System.out.println(user1);
        });
    }

    @Test
    void saveUser() {
        given(userRepository.save(user)).willReturn(user);

        User user1 = userService.saveUser(user);
        assertThat(user1).isNotNull();
    }

    @Test
    void deleteUser() {
        willDoNothing().given(userRepository).delete(user);
        userService.deleteUser(user);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void getUserById() {
        given(userRepository.findUserById(user.getId())).willReturn(user);
        User user1 = userService.getUserById(1L);
        assertEquals(user, user1);
    }
}