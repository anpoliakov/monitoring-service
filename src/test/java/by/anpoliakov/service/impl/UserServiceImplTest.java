//package by.anpoliakov.service.impl;
//
//import by.anpoliakov.domain.entity.User;
//import by.anpoliakov.domain.enums.RoleType;
//import by.anpoliakov.exception.UserException;
//import by.anpoliakov.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
////@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private static UserRepository repoUser;
//
//    private User user;
//
//    @BeforeEach
//    public void prepareUser() {
//        String login = "testLogin";
//        String password = "testPassword";
//        RoleType role = RoleType.USER;
//        user = new User(login, password, role);
//    }
//
//    @Test
//    @DisplayName("Method get user by login should return object User")
//    void getByLogin_shouldReturnUser() {
//        Mockito.when(repoUser.findByLogin(user.getLogin())).thenReturn(Optional.ofNullable(user));
//
//        User actualUser = userService.getByLogin(user.getLogin());
//        Assertions.assertNotNull(actualUser);
//        Assertions.assertEquals(user, actualUser);
//    }
//
//    @Test
//    @DisplayName("User not exist - method get by login should throw UserException")
//    void getByLogin_UserNotExist_throwUserException() {
//        Mockito.when(repoUser.findByLogin(anyString())).thenReturn(Optional.ofNullable(null));
//
//        assertThrows(UserException.class, () -> userService.getByLogin(user.getLogin()));
//    }
//
//    @Test
//    @DisplayName("Method get all users - should return map users")
//    void getAllUsers_shouldReturnMapUsers() {
//        Map<String, User> users = new HashMap<>();
//        users.put("login1", new User("login1", "password1", RoleType.USER));
//        users.put("login2", new User("login2", "password2", RoleType.USER));
//
//        Mockito.when(repoUser.findAllUsers()).thenReturn(users);
//
//        Map<String, User> actualUsers = userService.getAllUsers();
//        Assertions.assertEquals(2, actualUsers.size());
//    }
//
//    @Test
//    @DisplayName("Method get all users - throw UserException when database of users are empty")
//    void getAllUsers_databaseOfUsersEmpty_throwUserException() {
//        Mockito.when(repoUser.findAllUsers()).thenReturn(null);
//
//        assertThrows(UserException.class, () -> userService.getAllUsers());
//    }
//
//    @Test
//    @DisplayName("Method for change role - should change role user")
//    void changeUserRole_shouldChangeRoleUser() {
//        RoleType newRole = RoleType.ADMIN;
//
//        when(repoUser.findByLogin(user.getLogin())).thenReturn(Optional.ofNullable(user));
//        when(repoUser.updateRoleUser(user, newRole)).thenReturn(true);
//
//        userService.changeUserRole(user, newRole);
//        Assertions.assertEquals(user.getRoleType(), newRole);
//    }
//}