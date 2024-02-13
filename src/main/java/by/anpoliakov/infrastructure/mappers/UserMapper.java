package by.anpoliakov.infrastructure.mappers;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto convertUserToDto(User user);
    User convertDtoToUser(UserDto userDto);
}
