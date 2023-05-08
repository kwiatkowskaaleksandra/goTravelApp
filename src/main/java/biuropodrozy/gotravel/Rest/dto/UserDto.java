package biuropodrozy.gotravel.Rest.dto;/*
 * @project gotravel
 * @author kola
 */

public record UserDto(Long id, String username, String firstname, String lastname, String email, String role, int phoneNumber, String city, String street, String streetNumber, int zipCode) {

}
