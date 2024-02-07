package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.exception.AuthenticationException;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.service.AuthenticationService;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * Сервис бизнес-логики по авторизации/регистрации пользователя
 */
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;

    /**
     * Метод для авторизации пользователя
     *
     * @param login    - логин
     * @param password - пароль
     * @return объект User из БД
     */
    public User authorize(String login, String password) throws AuthenticationException {

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
     * Регистрация нового пользователя
     */
    public User register(String login, String password) throws AuthenticationException {
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
