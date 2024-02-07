package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;

import java.util.Map;
import java.util.Optional;

/**
 * Интерфейс определяющий сигнатуры методов CRUD операций с объектами типа User
 */
public interface UserRepository {
    /**
     * Добавление нового пользователя
     */
    Optional<User> create(User user);

    /**
     * Получение пользователя по логину
     */
    Optional<User> findByLogin(String login);

    /**
     * Получение всех пользователей
     */
    Map<String, User> findAllUsers();

    boolean updateRoleUser(User user, RoleType role);
}
