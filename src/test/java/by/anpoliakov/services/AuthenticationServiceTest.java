package by.anpoliakov.services;

import by.anpoliakov.domain.entities.User;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    static public UserRepositoryImpl instance;
    static String login = "Login";
    static String name = "Andrew";
    static String password = "Password";

    @BeforeAll
    static void prepareUserRepository(){
        instance = UserRepositoryImpl.getInstance();
        instance.add(new User(name, login, password));
    }

    @Test
    void loginUser_() {
    }

    @Test
    void registerUser() {
    }

    @Test
    void getRoleCurrentUser() {
    }
}