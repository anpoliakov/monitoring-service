package by.anpoliakov.service;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;

import java.util.Map;

public interface UserService {
    User getByLogin(String loginUser);

    Map<String, User> getAllUsers();

    void changeUserRole(User user, RoleType roleType);
}
