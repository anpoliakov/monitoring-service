package by.anpoliakov.service;

import by.anpoliakov.domain.entity.User;

/** Интерфейс определяющий сигнатуры методов для регистрации и авторизации пользователя*/
public interface AuthenticationService {
    /**
     * Авторизация пользователя при помощи логина и пароля
     *
     * @param login - логин пользователя
     * @param password - пароль пользователя
     * @return User - конкретный объект полученный из БД
     * */
    User authorize(String login, String password);

    /**
     * Регистрация нового пользователя
     *
     * @param name - имя нового пользователя
     * @param login - логин нового пользователя
     * @param password - пароль нового пользователя
     * */
    User register(String name, String login, String password);
}
