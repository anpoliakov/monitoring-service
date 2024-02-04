package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.util.ConnectionManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Реализация интерфейса UserRepository и
 * предоставляет методы для взаимодействия с таблицей users в БД */
public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private Statement st = null;

    private UserRepositoryImpl() {}

    public static UserRepository getInstance(){
        if (instance == null){
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> add(User user) {
        try {
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement (ConstantsSQL.CREATE_USER);
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRoleType().name());
            pst.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            rollBack(connection, e);
        }finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return getByLogin(user.getLogin());
    }

    @Override
    public Optional<User> getByLogin(String login) {
        User user = null;

        try {
            connection = ConnectionManager.createConnection();

            pst = connection.prepareStatement(ConstantsSQL.SELECT_USER_BY_LOGIN);
            pst.setString(1, login);
            rs = pst.executeQuery();

            while (rs.next()){
                BigInteger userId = rs.getBigDecimal(ConstantsSQL.USER_ID_LABEL).toBigInteger();
                String userLogin = rs.getString(ConstantsSQL.USER_LOGIN_LABEL);
                String userPassword = rs.getString(ConstantsSQL.USER_PASSWORD_LABEL);
                RoleType roleType = RoleType.valueOf(rs.getString(ConstantsSQL.ROLE_TYPE_NAME_LABEL));

                user = new User(userId, userLogin, userPassword, roleType);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Map<String, User> getAllUsers() {
        Map <String, User> users = null;

        try {
            connection = ConnectionManager.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery(ConstantsSQL.SELECT_ALL_USERS);
            users = new HashMap<>();

            while (rs.next()){
                BigInteger userId = rs.getBigDecimal(ConstantsSQL.USER_ID_LABEL).toBigInteger();
                String userLogin = rs.getString(ConstantsSQL.USER_LOGIN_LABEL);
                String userPassword = rs.getString(ConstantsSQL.USER_PASSWORD_LABEL);
                RoleType roleType = RoleType.valueOf(rs.getString(ConstantsSQL.ROLE_TYPE_NAME_LABEL));

                users.put(userLogin, new User(userId, userLogin, userPassword, roleType));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(st);
            ConnectionManager.closeConnection();
        }

        return users;
    }

    /**
     * Метод для отмены транзакции
     * */
    private void rollBack(Connection connection, SQLException e){
        System.err.println("Произошла ошибка: " + e.getMessage());
        if (connection != null) {
            try {
                connection.rollback();
                System.err.println("Транзакция отменена.");
            } catch (SQLException rollbackException) {
                System.err.println("Ошибка при откате транзакции: " + rollbackException.getMessage());
            }
        }
    }
}
