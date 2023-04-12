package biuropodrozy.gotravel.Mapper;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.User;
import biuropodrozy.gotravel.Rest.dto.UserDto;

public interface UserMapper {
    UserDto toUserDto(User user);
}
