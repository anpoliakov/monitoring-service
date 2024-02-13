package by.anpoliakov.service.impl;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.exception.AuthenticationException;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.service.AuthenticationService;

import java.util.Optional;

/**
 * Сервис бизнес-логики по авторизации/регистрации пользователя
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод для авторизации пользователя
     *
     * @param userDto - data transfer object сущности user
     * @return объект User полученный из БД
     */
    public User authorize(UserDto userDto) throws AuthenticationException {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("User [" + login + "] is not found in the database!");
        }

        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Wrong password!");
        }

        return user;
    }

    /**
     * Метод для регистрации нового пользователя
     *
     * @param userDto - data transfer object сущности user
     * @return внесённый объект User в БД
     */
    public User register(UserDto userDto) throws AuthenticationException {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Field/Fields cannot be empty!");
        }

        Optional<User> optionalUser = userRepository.create(new User(login, password, RoleType.USER));
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("User already exist!");
        }

        return optionalUser.get();
    }
}
