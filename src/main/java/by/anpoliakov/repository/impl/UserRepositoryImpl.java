package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.enums.Role;
import by.anpoliakov.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

/** Реализация интерфейса репозитория (локальная БД пользователей) */
public class UserRepositoryImpl implements UserRepository {
    private static UserRepositoryImpl instance;
    private Map<String, User> users;

    private UserRepositoryImpl() {
        users = new HashMap<>(5);
        User admin = new User("Administrator", "admin", "admin");
        admin.setRole(Role.ADMIN);
        add(admin);
    }

    /** Singleton метод для работы с одной базой данных  */
    public static UserRepositoryImpl getInstance(){
        if (instance == null){
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User add(User user) {
        String login = user.getLogin();
        users.put(login, user);
        return users.get(login);
    }

    @Override
    public User getByLogin(String login) {
        return users.get(login);
    }

    @Override
    public boolean exist(String login) {
        return users.containsKey(login);
    }

    @Override
    public Map<String, User> getAllUsers() {
        return users;
    }
}
