package by.anpoliakov.services;

import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.enums.Role;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.repository.impl.UserRepositoryImpl;

/** Сервис по аутентификации/регистрации пользователя */
public class AuthenticationService {
    private UserRepository userRepo;

    public AuthenticationService() {
        this.userRepo = UserRepositoryImpl.getInstance();
    }

    //Аутентификация пользователя
    public User loginUser(String login, String password){
        User user = null;

        if(userRepo.exist(login)){
            User checkingUser = userRepo.getByLogin(login);

            if(checkingUser.getPassword().equals(password)){
                user = checkingUser;
            }
        }

        return user;
    }

    //Регистрация пользователя
    public User registerUser(String name, String login, String password){
        User user = null;

        if(!userRepo.exist(login)){
            user = userRepo.add(new User(name, login, password));
        }else {
            System.out.println("User [" + login + "] exists! Try again!");
        }

        return user;
    }

    public Role getRoleCurrentUser(User user){
        return user.getRole();
    }
}
