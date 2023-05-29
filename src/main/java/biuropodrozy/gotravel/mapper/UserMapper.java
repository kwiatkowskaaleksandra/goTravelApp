package biuropodrozy.gotravel.mapper;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.UserDto;

public interface UserMapper {
    UserDto toUserDto(User user);
}
