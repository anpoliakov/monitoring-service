package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private static UserRepository repoUser;

    private User actualUser;

    @BeforeEach
    public void prepareRepo(){
        String name = "testName";
        String login = "testLogin";
        String password = "testPassword";
        RoleType role = RoleType.USER;

        actualUser = new User(login, password, role);
        repoUser.add(actualUser);
    }

    @Test
    void getAllUsers_shouldReturnNotNullMap() {
        //given
        HashMap <String, User> users = new HashMap<>();
        users.put(actualUser.getLogin(), actualUser);

        //when
        when(repoUser.getAllUsers()). thenReturn(users);

        //then
        Map<String, User> allUsers = userService.getAllUsers();
        Assertions.assertNotNull(allUsers);
        Assertions.assertEquals(1, allUsers.size());
    }

    @Test
    void getUserByLogin_shouldReturnUser() {
        //given
        String loginUser = actualUser.getLogin();

        //when
        when(repoUser.getByLogin(loginUser)).thenReturn(Optional.ofNullable(actualUser));

        //then
        User expectedUser = userService.getByLogin(actualUser.getLogin());
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void changeUserRole_shouldChangeRole_userHasRoleAdmin() {
        //given
        RoleType newRole = RoleType.ADMIN;
        String loginUser = actualUser.getLogin();

        //when
        when(repoUser.getByLogin(loginUser)).thenReturn(Optional.ofNullable(actualUser));

        userService.changeUserRole(actualUser, newRole);

        User expectedUser = userService.getByLogin(actualUser.getLogin());
        Assertions.assertEquals(expectedUser.getRoleType(), newRole);
    }
}