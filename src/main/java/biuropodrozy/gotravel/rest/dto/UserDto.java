package biuropodrozy.gotravel.rest.dto;

/**
 * The record of User dto.
 * @param id the user id
 * @param username the username
 * @param firstname the firstname
 * @param lastname the lastname
 * @param email the email
 * @param role the role
 * @param phoneNumber the phone number
 * @param city the city
 * @param street the street
 * @param streetNumber the street number
 * @param zipCode the zip code
 */
public record UserDto(Long id, String username, String firstname, String lastname, String email, String role,
                      String phoneNumber, String city, String street, String streetNumber, String zipCode) {

}
