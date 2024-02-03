package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.ActionType;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.exception.AuthenticationException;
import by.anpoliakov.infrastructure.ConsoleInterface;
import by.anpoliakov.repository.AuditLogRepository;
import by.anpoliakov.repository.MeterReadingRepository;
import by.anpoliakov.repository.MeterTypeRepository;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.repository.impl.AuditLogRepositoryImpl;
import by.anpoliakov.repository.impl.MeterReadingRepositoryImpl;
import by.anpoliakov.repository.impl.MeterTypeRepositoryImpl;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.service.*;
import by.anpoliakov.service.impl.AuditLogServiceImpl;
import by.anpoliakov.service.impl.MeterReadingServiceImpl;
import by.anpoliakov.service.impl.MeterTypeServiceImpl;
import by.anpoliakov.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class MainConsole implements ConsoleInterface {
    private AuditLogService auditLogService;
    private AuthenticationService authService;

    /** Основное меню программы */
    public void showMainMenu() {
        int choice = -1;

        do{
            System.out.println("----------- Main menu -----------");
            System.out.println("[1] Authorization");
            System.out.println("[2] Registration");
            System.out.println("[0] Exit");
            System.out.print("Enter the option selected:");

            switch (choice = getInputNumberMenu()){
                case 1 -> {showLoginMenu();}
                case 2 -> {showRegistrationMenu();}
                case 0 -> {}
                default -> System.out.println("Incorrect menu selection, please try again:");
            }
        } while (choice != 0);
    }

    /**
     * Меню с авторизацией пользователя
     * */
    public void showLoginMenu() {
        System.out.println("----------- Authorization -----------");
        System.out.println("(enter [0] to return to the main menu)");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your login: ");
        java.lang.String login = scanner.nextLine().trim();
        if(login.equals("0")){
            return;
        }

        System.out.println("Enter your password: ");
        java.lang.String password = scanner.nextLine().trim();
        if(password.equals("0")){
            return;
        }

        try {
            User user = authService.authorize(login, password);
            ConsoleInterface console = getSpecificConsoleByUser(user);
            auditLogService.addAuditLog(user, ActionType.AUTHORIZATION);

            console.showMainMenu();
        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            showLoginMenu();
        }
    }

    /**
     * Меню регистрации пользователя
     * */
    public void showRegistrationMenu() {
        System.out.println("----------- Registration -----------");
        System.out.println("(enter [0] to return to the main menu)");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name: ");
        java.lang.String name = scanner.nextLine().trim();
        if(name.equals("0")){
            return;
        }

        System.out.println("Enter your login: ");
        java.lang.String login = scanner.nextLine().trim();
        if(login.equals("0")){
            return;
        }

        System.out.println("Enter your password: ");
        java.lang.String password = scanner.nextLine().trim();
        if(password.equals("0")){
            return;
        }

        try{
            User user = authService.register(name, login, password);
            ConsoleInterface console = getSpecificConsoleByUser(user);
            console.showMainMenu();
        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }
    }

    /**
     * Метод, который отдаёт нужный тип консоли под определённого пользователя
     *
     * @param user - пользователь с имеющимся полем RoleType
     * @return конкретная реализация интерфейса ConsoleInterface под определённую
     * роль пользователя */
    private ConsoleInterface getSpecificConsoleByUser(User user){
        ConsoleInterface console = null;

        MeterReadingRepository repoMeterReading = MeterReadingRepositoryImpl.getInstance();
        MeterTypeRepository repoMeterType = MeterTypeRepositoryImpl.getInstance();
        AuditLogRepository repoAuditLog = AuditLogRepositoryImpl.getInstance();
        UserRepository repoUser = UserRepositoryImpl.getInstance();

        MeterTypeService meterTypeService = new MeterTypeServiceImpl(repoMeterType);
        MeterReadingService meterReadingService = new MeterReadingServiceImpl(repoMeterReading, meterTypeService);
        AuditLogService auditLogService = new AuditLogServiceImpl(repoAuditLog);
        UserService userService = new UserServiceImpl(repoUser);

        if(RoleType.USER.equals(user.getRoleType())){
            console = new UserConsole(user,meterReadingService,meterTypeService,auditLogService);
        }

        if(RoleType.ADMIN.equals(user.getRoleType())){
            console = new AdminConsole(user, meterReadingService, meterTypeService, userService, auditLogService);
        }

        return console;
    }
}
