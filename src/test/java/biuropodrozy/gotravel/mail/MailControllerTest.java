package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MailControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock private MailService mailService;
    @InjectMocks
    private MailController mailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mailController).build();
    }

    @Test
    public void sendConfirmationEmail_ValidUser_ReturnsOk() throws Exception {
        String username = "user123";
        User mockUser = new User();
        mockUser.setUsername(username);
        when(userService.validateAndGetUserByUsername(username)).thenReturn(mockUser);

        mockMvc.perform(get("/mail/sendConfirmationEmail/{username}", username))
                .andExpect(status().isOk());

        verify(mailService).confirmationEmail(mockUser);
        verify(userService).validateAndGetUserByUsername(username);
    }

    @Test
    public void sendConfirmationNewEmail_ValidEmails_ReturnsOk() throws Exception {
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";

        doNothing().when(userService).updateUserEmail(oldEmail, newEmail);

        mockMvc.perform(get("/mail/sendConfirmationNewEmail/{oldEmail}", oldEmail)
                        .param("newEmail", newEmail))
                .andExpect(status().isOk());

        verify(userService).updateUserEmail(oldEmail, newEmail);
    }

    @Test
    public void confirmEmail_ValidDetails_ThrowsException() throws Exception {
        String username = "user123";
        String email = "user123@example.com";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setEmail(email);

        String encodedEmail = Base64.getEncoder().encodeToString(email.getBytes(StandardCharsets.UTF_8));
        String encodedUsername = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));

        when(userService.validateAndGetUserByUsername(username)).thenReturn(mockUser);

        mockMvc.perform(put("/mail/confirmEmail")
                        .param("newEmail", encodedEmail)
                        .param("username", encodedUsername))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserException))
                .andExpect(result -> assertEquals("Link error.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void confirmEmail_LinkError_ReturnsOk() throws Exception {
        String username = "user123";
        String email = "wrong@example.com";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setEmail("user123@example.com");

        String encodedEmail = Base64.getEncoder().encodeToString(email.getBytes(StandardCharsets.UTF_8));
        String encodedUsername = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));

        when(userService.validateAndGetUserByUsername(username)).thenReturn(mockUser);

        mockMvc.perform(put("/mail/confirmEmail")
                        .param("newEmail", encodedEmail)
                        .param("username", encodedUsername))
                .andExpect(status().isOk());

        verify(userService).validateAndGetUserByUsername(username);
        verify(userService).save(mockUser);
    }
}