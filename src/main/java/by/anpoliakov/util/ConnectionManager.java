package by.anpoliakov.util;

import by.anpoliakov.infrastructure.constant.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Класс реализованный по паттерну singleton предоставляет Connection к базе данных
 * так же занимается закрытием: Connection, Statement, ResultSet
 * */
public class ConnectionManager {
    private static Connection conn = null;

    static {
        try {
            Class.forName(Constants.DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionManager() {}

    public static Connection createConnection (){
        try {
            if(conn == null || conn.isClosed()){
                Properties properties = loadDatabaseProperties();

                conn = DriverManager.getConnection(
                        properties.getProperty("URL"),
                        properties.getProperty("USER_NAME"),
                        properties.getProperty("PASSWORD"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            //TODO: исправить
            System.err.println("Проверьте наличие необходимых ключей=значений в файле database.properties");
        }

        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Properties loadDatabaseProperties(){
        Properties properties = null;

        try (FileInputStream fis = new FileInputStream(Constants.PATH_TO_DB_PROPERTIES)) {
            properties = new Properties();
            properties.load(fis);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    /**
     * Метод для отмены транзакции
     * */
    //TODO в процессе поправки Exception
    public static void rollBack(Connection connection){
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.err.println("Ошибка при откате транзакции: " + rollbackException.getMessage());
            }
        }
    }
}

