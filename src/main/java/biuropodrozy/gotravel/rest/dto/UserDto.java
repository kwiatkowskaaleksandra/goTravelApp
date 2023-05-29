package biuropodrozy.gotravel.rest.dto;/*
 * @project gotravel
 * @author kola
 */

public record UserDto(Long id, String username, String firstname, String lastname, String email, String role,
                      String phoneNumber, String city, String street, String streetNumber, String zipCode) {

}
