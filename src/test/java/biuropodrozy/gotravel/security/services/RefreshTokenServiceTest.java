package biuropodrozy.gotravel.security.services;

import biuropodrozy.gotravel.exception.TokenRefreshException;
import biuropodrozy.gotravel.refreshToken.RefreshToken;
import biuropodrozy.gotravel.refreshToken.RefreshTokenRepository;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(60000));
        refreshToken.setToken(UUID.randomUUID().toString());
        Field field = RefreshTokenService.class.getDeclaredField("refreshTokenExpiration");
        field.setAccessible(true);
        field.set(refreshTokenService, 3600000L);
    }

    @Test
    void testFindByToken_Found() {
        String token = "some-token";
        Optional<RefreshToken> expected = Optional.of(new RefreshToken());
        when(refreshTokenRepository.findByToken(token)).thenReturn(expected);

        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        assertSame(expected, result);
        verify(refreshTokenRepository).findByToken(token);
    }

    @Test
    void testFindByToken_NotFound() {
        String token = "non-existent-token";
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        assertTrue(result.isEmpty());
        verify(refreshTokenRepository).findByToken(token);
    }

    @Test
    void testCreateRefreshToken_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        RefreshToken refreshToken = new RefreshToken();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(refreshTokenRepository.save(isA(RefreshToken.class))).thenReturn(refreshToken);

        RefreshToken createdToken = refreshTokenService.createRefreshToken(userId);

        assertNotNull(createdToken);
        verify(userRepository).findById(userId);
        verify(refreshTokenRepository).save(isA(RefreshToken.class));
    }

    @Test
    void testCreateRefreshToken_UserNotFound() {
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> refreshTokenService.createRefreshToken(userId));

        assertEquals("User not found with ID: " + userId, exception.getMessage());
    }

    @Test
    void testVerifyExpiration_NotExpired() {
        RefreshToken result = refreshTokenService.verifyExpiration(refreshToken);
        assertNotNull(result);
    }

    @Test
    void testVerifyExpiration_Expired() {
        refreshToken.setExpiryDate(Instant.now().minusMillis(1000));
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(refreshToken));
    }

    @Test
    void deleteByUserId_ExistingUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        refreshTokenService.deleteByUserId(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(refreshTokenRepository, times(1)).deleteByUser(existingUser);
    }

    @Test
    void deleteByUserId_UserNotFound() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> refreshTokenService.deleteByUserId(userId));

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }
}