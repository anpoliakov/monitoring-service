package by.anpoliakov.util;

import by.anpoliakov.infrastructure.constant.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Класс реализованный по паттерну singleton предоставляет Connection к базе данных
 * и занимается закрытием: Connection, Statement, ResultSet
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

    /**
     * Метод выдаёт объект, отвечающий за соединение с базой данных
     *
     * @return объект Connection
     * */
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
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * Метод для закрытия Connection
     * */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для закрытия Statement
     * */
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для закрытия ResultSet
     * */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод предоставляющий класс Properties для взаимодействия с
     * файлом настроек подключения к бд
     *
     * @return Properties - объект для работы с файлом properties
     * путь к файлу прописан в статическом классе констант - PATH_TO_DB_PROPERTIES
     * */
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

