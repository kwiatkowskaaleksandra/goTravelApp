package biuropodrozy.gotravel.userTripPreferences;

import biuropodrozy.gotravel.exception.UserTripPreferencesException;
import biuropodrozy.gotravel.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserTripPreferencesServiceImplTest {

    private UserTripPreferencesServiceImpl userTripPreferencesService;
    @Mock private  UserTripPreferencesRepository userTripPreferencesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userTripPreferencesService = new UserTripPreferencesServiceImpl(userTripPreferencesRepository);
    }

    @Test
    void getUserTripPreferences_success() {
        User user = new User();
        UserTripPreferences userTripPreferences = new UserTripPreferences();

        when(userTripPreferencesRepository.findByUser(user)).thenReturn(Optional.of(userTripPreferences));

        UserTripPreferences result = userTripPreferencesService.getUserTripPreferences(user);

        assertEquals(userTripPreferences, result);
    }

    @Test
    void getUserTripPreferences_shouldThrowException() {
        User user = new User();
        when(userTripPreferencesRepository.findByUser(user)).thenReturn(Optional.empty());

        UserTripPreferencesException exception = assertThrows(UserTripPreferencesException.class, () -> userTripPreferencesService.getUserTripPreferences(user));

        assertEquals("The user has no preferences added.", exception.getMessage());
    }

    @Test
    void savePreferences_shouldThrowException() {
        User user = new User();
        UserTripPreferences userTripPreferences = new UserTripPreferences(1L, -1, 1, -1, 2, -1, user);

        UserTripPreferencesException exception = assertThrows(UserTripPreferencesException.class, () -> userTripPreferencesService.savePreferences(user, userTripPreferences));

        assertEquals("preferencesHaveBeenEnteredIncorrectly", exception.getMessage());
        verify(userTripPreferencesRepository, never()).save(any(UserTripPreferences.class));
    }

    @Test
    void savePreferences_userTripPreferencesIsNotNull() {
        User user = new User();
        UserTripPreferences existingPreferences  = new UserTripPreferences(1L, 1, 1, 1, 2, 1, user);

        when(userTripPreferencesRepository.findByUser(user)).thenReturn(Optional.of(existingPreferences));
        when(userTripPreferencesRepository.save(existingPreferences)).thenReturn(existingPreferences);

        UserTripPreferences result = userTripPreferencesService.savePreferences(user, existingPreferences );

        assertNotNull(result);
        assertEquals(existingPreferences, result);
        verify(userTripPreferencesRepository, times(1)).save(existingPreferences);
    }

    @Test
    void savePreferences_userTripPreferencesIsNull() {
        User user = new User();
        UserTripPreferences userTripPreferences  = new UserTripPreferences(1L, 1, 1, 1, 2, 1, user);

        when(userTripPreferencesRepository.findByUser(user)).thenReturn(Optional.empty());
        when(userTripPreferencesRepository.save(userTripPreferences)).thenReturn(userTripPreferences);

        UserTripPreferences result = userTripPreferencesService.savePreferences(user, userTripPreferences );

        assertNotNull(result);
        assertEquals(userTripPreferences, result);
        verify(userTripPreferencesRepository, times(1)).save(userTripPreferences);
    }

}