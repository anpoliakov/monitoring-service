package by.anpoliakov.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс реализованный по паттерну singleton предоставляет Connection к базе данных
 * и занимается закрытием: ResultSet
 */
public class ConnectionManager {
    private static Connection connection = null;

    static {
        try {
            Class.forName(PropertiesLoader.getProperty("database.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionManager() {
    }

    /**
     * Метод выдаёт объект, отвечающий за соединение с базой данных
     *
     * @return объект Connection
     */
    public static Connection createConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(
                        PropertiesLoader.getProperty("database.url"),
                        PropertiesLoader.getProperty("database.username"),
                        PropertiesLoader.getProperty("database.password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Метод для закрытия ResultSet
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

