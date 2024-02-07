package by.anpoliakov;

import by.anpoliakov.infrastructure.constant.Constants;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.infrastructure.in.MainConsole;
import by.anpoliakov.repository.AuditLogRepository;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.repository.impl.AuditLogRepositoryImpl;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.service.AuditLogService;
import by.anpoliakov.service.AuthenticationService;
import by.anpoliakov.service.impl.AuditLogServiceImpl;
import by.anpoliakov.service.impl.AuthenticationServiceImpl;
import by.anpoliakov.util.ConnectionManager;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Входная точка приложения
 */
public class RunnerApplication {
    public static void main(String[] args) {
        prepareDataBase();

        UserRepository repoUser = UserRepositoryImpl.getInstance();
        AuditLogRepository repoAuditLog = AuditLogRepositoryImpl.getInstance();

        AuditLogService auditLogService = new AuditLogServiceImpl(repoAuditLog);
        AuthenticationService authService = new AuthenticationServiceImpl(repoUser);

        MainConsole mainConsole = new MainConsole(auditLogService, authService);
        mainConsole.showMainMenu();
    }

    /**
     * Метод для выполнения миграций с помощью Liquibase
     */
    public static void prepareDataBase() {
        createSystemSchemeLiquibase();
        Database database = null;

        try (Connection connection = ConnectionManager.createConnection()) {

            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(ConstantsSQL.NAME_SYSTEM_SCHEMA_LIQUIBASE);
            Liquibase liquibase = new Liquibase(Constants.PATH_TO_CHANGELOG_FILE, new ClassLoaderResourceAccessor(), database);
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

            st.execute("CREATE SCHEMA IF NOT EXISTS " + ConstantsSQL.NAME_SYSTEM_SCHEMA_LIQUIBASE);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
