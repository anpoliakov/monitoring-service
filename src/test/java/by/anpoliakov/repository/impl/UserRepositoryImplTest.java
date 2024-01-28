package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entities.User;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

class UserRepositoryImplTest {
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
    void getInstance_shouldReturnInstance() {
        Assert.assertEquals(instance, UserRepositoryImpl.getInstance());
    }

    @Test
    void add_userShouldBeAdded() {
        String testName = "Andrew";
        String testLogin = "LoginTest";
        String testPassword = "PasswordTest";
        instance.add(new User(testName, testLogin, testPassword));

        Assert.assertTrue(instance.exist(testLogin));
    }

    @Test
    void getByLogin_methodShouldReturnUserByLogin() {
        User user = instance.getByLogin(login);
        Assert.assertNotNull(user);
    }

    @Test
    void exist_methodShouldReturnTrue() {
        Assert.assertTrue(instance.exist(login));
    }

    @Test
    void getAllUsers_methodReturnsNotNullMap() {
        Map<String, User> users = instance.getAllUsers();
        Assert.assertNotNull(users);
    }
}