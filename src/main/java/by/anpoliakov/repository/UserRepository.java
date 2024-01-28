package by.anpoliakov.repository;

import by.anpoliakov.domain.entities.User;

import java.util.Map;

public interface UserRepository {
    /** Добавление нового пользователя */
    User add(User user);
    /** Получение пользователя по логину */
    User getByLogin(String login);
    /** Проверка - существует ли пользователь в БД */
    boolean exist(String login);
    /** Получение всех пользователей */
    Map<String, User> getAllUsers();
}
