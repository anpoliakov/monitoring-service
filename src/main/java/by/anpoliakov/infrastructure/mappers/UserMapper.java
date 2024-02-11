package by.anpoliakov.infrastructure.mappers;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto userToDto(User user);
}
