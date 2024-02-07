package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.util.ConnectionManager;

import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация интерфейса UserRepository и
 * предоставляет методы для взаимодействия с таблицей users в БД
 */
public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> create(User user) {
        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.CREATE_USER)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRoleType().name());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return findByLogin(user.getLogin());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        ResultSet resultSet = null;
        User user = null;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.SELECT_USER_BY_LOGIN)) {

            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BigInteger userId = resultSet.getBigDecimal(ConstantsSQL.USER_ID_LABEL).toBigInteger();
                String userLogin = resultSet.getString(ConstantsSQL.USER_LOGIN_LABEL);
                String userPassword = resultSet.getString(ConstantsSQL.USER_PASSWORD_LABEL);
                RoleType roleType = RoleType.valueOf(resultSet.getString(ConstantsSQL.ROLE_TYPE_NAME_LABEL));

                user = new User(userId, userLogin, userPassword, roleType);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Map<String, User> findAllUsers() {
        ResultSet resultSet = null;
        Map<String, User> users = null;

        try (Connection connection = ConnectionManager.createConnection();
             Statement statement = connection.createStatement()) {

            resultSet = statement.executeQuery(ConstantsSQL.SELECT_ALL_USERS);
            users = new HashMap<>();

            while (resultSet.next()) {
                BigInteger userId = resultSet.getBigDecimal(ConstantsSQL.USER_ID_LABEL).toBigInteger();
                String userLogin = resultSet.getString(ConstantsSQL.USER_LOGIN_LABEL);
                String userPassword = resultSet.getString(ConstantsSQL.USER_PASSWORD_LABEL);
                RoleType roleType = RoleType.valueOf(resultSet.getString(ConstantsSQL.ROLE_TYPE_NAME_LABEL));

                users.put(userLogin, new User(userId, userLogin, userPassword, roleType));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return users;
    }

    @Override
    public boolean updateRoleUser(User user, RoleType role) {
        boolean hasChange = false;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.UPDATE_ROLE_USER)) {

            preparedStatement.setString(1, role.name());
            preparedStatement.setString(2, user.getLogin());
            int valueChange = preparedStatement.executeUpdate();
            if(valueChange != 0){
                hasChange = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hasChange;
    }
}
