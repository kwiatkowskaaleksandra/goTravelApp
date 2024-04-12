package biuropodrozy.gotravel.mail;

import biuropodrozy.gotravel.exception.UserException;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Controller for handling mail-related operations.
 * This controller provides endpoints for sending confirmation emails and processing email confirmation.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {

    /**
     * Service for managing user-related operations.
     */
    private final UserService userService;

    /**
     * Service for sending emails.
     */
    private final MailService mailService;

    /**
     * Sends a confirmation email to the user with the specified username.
     * This endpoint sends a confirmation email to the user with the provided username.
     *
     * @param username The username of the user to whom the confirmation email will be sent.
     * @return ResponseEntity with status 200 OK if the email is sent successfully.
     */
    @GetMapping("/sendConfirmationEmail/{username}")
    public ResponseEntity<User> sendConfirmationEmail(@PathVariable String username) {
        User existingUser = userService.validateAndGetUserByUsername(username);
        mailService.confirmationEmail(existingUser);
        return ResponseEntity.ok().build();
    }

    /**
     * Sends a confirmation email for updating the email address.
     * This endpoint sends a confirmation email to update the email address of the user
     * with the specified old email address to the new email address.
     *
     * @param oldEmail The current email address of the user.
     * @param newEmail The new email address to be assigned to the user.
     * @return ResponseEntity with status 200 OK if the email is sent successfully.
     */
    @GetMapping("/sendConfirmationNewEmail/{oldEmail}")
    public ResponseEntity<User> sendConfirmationNewEmail(@PathVariable String oldEmail, @RequestParam("newEmail") String newEmail) {
        userService.updateUserEmail(oldEmail, newEmail);
        return ResponseEntity.ok().build();
    }

    /**
     * Confirms the email address update using the provided confirmation link.
     * This endpoint confirms the email address update by comparing the new email address
     * and the username extracted from the provided Base64-encoded strings.
     *
     * @param newEmail The Base64-encoded new email address.
     * @param username The Base64-encoded username.
     * @return ResponseEntity with status 200 OK if the email address update is confirmed successfully.
     * @throws UserException If there is an error in the confirmation link.
     */
    @PutMapping("/confirmEmail")
    public ResponseEntity<?> confirmEmail(@RequestParam("newEmail") String newEmail, @RequestParam("username") String username) {
        byte[] decodeE = Base64.getDecoder().decode(newEmail);
        String emailDecode = new String(decodeE, StandardCharsets.UTF_8);
        byte[] decodeU = Base64.getDecoder().decode(username);
        String usernameDecode = new String(decodeU, StandardCharsets.UTF_8);
        User exisingUser = userService.validateAndGetUserByUsername(usernameDecode);
        System.out.println(exisingUser.getEmail().equals(emailDecode));
        if (!exisingUser.getEmail().equals(emailDecode)) {

            exisingUser.setEmail(emailDecode);
            userService.save(exisingUser);
            return ResponseEntity.ok().build();
        }
        else throw new UserException("Link error.");
    }
}
