package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.exception.UserException;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.service.UserService;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User getByLogin(String loginUser) throws UserException {
        if(loginUser.trim().isEmpty() || loginUser == null){
            throw new UserException("Login's user is empty or null");
        }

        Optional<User> user = userRepository.getByLogin(loginUser);
        if(user.isEmpty()){
            throw new UserException("User by login " + loginUser + " doesn't exist!");
        }

        return user.get();
    }

    @Override
    public Map<String, User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void changeUserRole(User user, RoleType roleType) throws UserException {
        if(user == null || roleType == null){
            throw new UserException("User of Role are null");
        }

        Optional<User> optionalUser = userRepository.getByLogin(user.getLogin());
        if(optionalUser.isEmpty()){
            throw new UserException("User is not exist!");
        }

        User exitUser = optionalUser.get();
        if(exitUser.getRoleType().compareTo(roleType) == 0){
            throw new UserException("The user already has this role installed!");
        }

        exitUser.setRoleType(roleType);
    }
}
