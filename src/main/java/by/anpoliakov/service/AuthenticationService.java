package by.anpoliakov.service;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.domain.entity.User;

/**
 * Интерфейс определяющий сигнатуры методов для
 * регистрации и авторизации пользователя
 */
public interface AuthenticationService {
    /**
     * Авторизация пользователя при помощи логина и пароля
     *
     * @param userDto - data transfer object сущности user
     * @return User - конкретный объект полученный из БД
     */
    User authorize(UserDto userDto);

    /**
     * Регистрация нового пользователя
     *
     * @param userDto - data transfer object сущности user
     * @return User - конкретный объект полученный из БД
     */
    User register(UserDto userDto);
}
