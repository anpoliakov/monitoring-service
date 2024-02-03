package by.anpoliakov;

import by.anpoliakov.infrastructure.in.MainConsole;
import by.anpoliakov.repository.AuditLogRepository;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.repository.impl.AuditLogRepositoryImpl;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.service.AuditLogService;
import by.anpoliakov.service.AuthenticationService;
import by.anpoliakov.service.impl.AuditLogServiceImpl;
import by.anpoliakov.service.impl.AuthenticationServiceImpl;

public class RunnerApplication {
    public static void main(String[] args) {
        UserRepository repoUser = UserRepositoryImpl.getInstance();
        AuditLogRepository repoAuditLog = AuditLogRepositoryImpl.getInstance();

        AuditLogService auditLogService = new AuditLogServiceImpl(repoAuditLog);
        AuthenticationService authService = new AuthenticationServiceImpl(repoUser);

        MainConsole mainConsole = new MainConsole(auditLogService, authService);
        mainConsole.showMainMenu();
    }
}
