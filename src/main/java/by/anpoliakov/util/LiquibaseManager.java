package by.anpoliakov.util;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс для запуска миграций баз данных с помощью Liquibase
 */
public class LiquibaseManager {
    public static void runMigrations() {
        createSystemSchemeLiquibase();

        try (Connection connection = ConnectionManager.createConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            database.setDefaultSchemaName(PropertiesLoader.getProperty("name-system-schema-liquibase"));
            Liquibase liquibase = new Liquibase(PropertiesLoader.getProperty("master-changelog"),
                    new ClassLoaderResourceAccessor(), database);

            liquibase.update();
            System.out.println("Миграции успешно выполнены!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Создание схемы в БД - для хранения системных таблиц liquibase
     */
    private static void createSystemSchemeLiquibase() {
        try (Connection connection = ConnectionManager.createConnection();
             Statement st = connection.createStatement()) {

            st.execute("CREATE SCHEMA IF NOT EXISTS " + PropertiesLoader.getProperty("name-system-schema-liquibase"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
