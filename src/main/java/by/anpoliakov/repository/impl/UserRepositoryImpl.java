package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация интерфейса UserRepository,
 * локальный репозиторий для хранения сущности User
 * */
public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance;
    private Map<String, User> users;

    private UserRepositoryImpl() {
        users = new HashMap<>();
        installInitialData();
    }

    public static UserRepository getInstance(){
        if (instance == null){
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> add(User user) {
        users.put(user.getLogin(), user);
        return Optional.ofNullable(users.get(user.getLogin()));
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.ofNullable(users.get(login));
    }

    @Override
    public Map<String, User> getAllUsers() {
        return users;
    }

    private void installInitialData(){
        User administrator = new User("Administrator","admin","admin", RoleType.ADMIN);
        User testUser = new User("TestName", "test", "test", RoleType.USER);
        users.put(administrator.getLogin(), administrator);
        users.put(testUser.getLogin(), testUser);
    }
}
