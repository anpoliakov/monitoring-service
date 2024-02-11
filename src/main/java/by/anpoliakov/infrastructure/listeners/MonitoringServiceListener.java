package by.anpoliakov.infrastructure.listeners;

import by.anpoliakov.util.LiquibaseManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Класс для запуска миграций баз данных, подготовки приложения к работе
 * */
@WebListener
public class MonitoringServiceListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LiquibaseManager.runMigrations();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
