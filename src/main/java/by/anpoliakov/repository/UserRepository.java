package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.User;

import java.util.Map;
import java.util.Optional;

/**
 * Интерфейс определяющий сигнатуры методов CRUD операций с объектами типа User
 * */
public interface UserRepository {
    /**
     * Добавление нового пользователя
     */
    Optional<User> add(User user);

    /**
     * Получение пользователя по логину
     */
    Optional<User> getByLogin(String login);

    /** Получение всех пользователей */
    Map<String, User> getAllUsers();
}
