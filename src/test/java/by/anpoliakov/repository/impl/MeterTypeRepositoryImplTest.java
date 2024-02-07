package by.anpoliakov.repository.impl;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

@Testcontainers
public class MeterTypeRepositoryImplTest {
    private static final String  DATABASE_NAME = "monitoring_service";
    private static final String  USER_NAME = "anpoliakov";
    private static final String  PASSWORD = "anpoliakov";
    private static final String CREATE_TABLE_METERS_TYPES = "CREATE TABLE meters_types " +
            "(meter_type_id SERIAL PRIMARY KEY, name VARCHAR (30) UNIQUE NOT NULL);";

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD);


    @BeforeAll
    /** Запуск docker контейнера с образом postgreSQL и создание таблицы*/
    static void beforeAll() {
        postgreSQLContainer.start();

        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword())) {

            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE_METERS_TYPES);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    /** Остановка контейнера docker с образом postgreSQL */
    public static void stopContainer() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Start image postgres in docker")
    void startDataBaseInDocker() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword())) {

            String insertQuery = "INSERT INTO meters_types (name) VALUES ('Test')";
            String selectQuery = "SELECT * FROM meters_types";

            try (Statement statement = connection.createStatement()) {
                statement.execute(insertQuery);
                statement.execute(selectQuery);

                try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                    while (resultSet.next()) {
                        Assertions.assertTrue(postgreSQLContainer.isRunning());
                        Assertions.assertEquals("Test", resultSet.getString("name"));
                    }
                }
            }
        }
    }
}