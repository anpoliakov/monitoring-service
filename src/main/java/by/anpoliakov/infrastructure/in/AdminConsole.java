package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entity.AuditLog;
import by.anpoliakov.domain.entity.MetersReadings;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.RoleType;
import by.anpoliakov.exception.MeterReadingException;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.exception.UserException;
import by.anpoliakov.infrastructure.ConsoleInterface;
import by.anpoliakov.service.AuditLogService;
import by.anpoliakov.service.MeterReadingService;
import by.anpoliakov.service.MeterTypeService;
import by.anpoliakov.service.UserService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@AllArgsConstructor
/**
 * Реализация интерфейса ConsoleInterface,
 * представляет собой консоль для администратора
 * */
public class AdminConsole implements ConsoleInterface {
    private User currentUser;
    private MeterReadingService meterReadingService;
    private MeterTypeService meterTypeService;
    private UserService userService;
    private AuditLogService auditLogService;

    /**
     * Главное меню данной консоли, точка входа в классе
     * */
    @Override
    public void showMainMenu() {
        int choice = -1;

        while (choice != 0){
            System.out.println("----------- Menu ADMIN -----------");
            System.out.println("[1] View the readings of all users");
            System.out.println("[2] Add type of Reading");
            System.out.println("[3] Change role of user");
            System.out.println("[4] View audit logs by user");
            System.out.println("[0] Exit");
            System.out.print("Enter the option selected:");

            switch (choice = getInputNumberMenu()){
                case 1 -> {showMetersReadingsUsers();}
                case 2 -> {createNewTypeMeter();}
                case 3 -> {changeRoleUser();}
                case 4 -> {showAuditLog();}
                case 0 -> {
                    choice = 0;
                }
                default -> System.out.println("Incorrect menu selection, please try again:");
            }
        }
    }

    /**
     * Метод для взаимодействия с администратором и добавлением нового счётчика
     * */
    public void createNewTypeMeter(){
        System.out.println("------- Menu: creating new type of meter -------");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new type: ");
        String newType = scanner.nextLine();

        try {
            meterTypeService.addMeterType(newType);
            System.out.println("Success!");

        }catch (MeterTypeException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }
    }

    /**
     * Метод для взаимодействия с администратором и предоставление всех показаний
     * */
    private void showMetersReadingsUsers() {
        System.out.println("------- Menu: show all meters readings users -------");
        Scanner scanner = new Scanner(System.in);
        Map<String, User> allUsers = userService.getAllUsers();

        System.out.println("Logins of users: ");
        for(Map.Entry<String, User> entry: allUsers.entrySet()){
            if(entry.getValue().getRoleType() == RoleType.USER){
                System.out.println(entry.getKey());
            }
        }

        System.out.println("Enter the user's login to display meters readings: ");
        String loginUser = scanner.nextLine();

        try {
            List<MetersReadings> metersReadingsByUser = meterReadingService.getMetersReadingsByUser(loginUser);
            for (MetersReadings metersReadings : metersReadingsByUser){
                System.out.println(metersReadings);
            }

            System.out.println("Press any key to continue...");
            scanner.nextLine();
        }catch (MeterReadingException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }
    }


    /**
     * Метод для взаимодействия с администратором и изменение роли у других пользователей
     * */
    public void changeRoleUser(){
        System.out.println("------- Menu: change role user -------");
        Scanner scanner = new Scanner(System.in);

        //показываю всех пользователей
        Map <String, User> allUsers = userService.getAllUsers();
        System.out.println("Exist user's login: ");
        for(Map.Entry<String, User> entry: allUsers.entrySet()){
            System.out.println("-> User: " + entry.getKey() + ", role: " + entry.getValue().getRoleType());
        }

        //показываю возможные р
        RoleType[] values = RoleType.values();
        System.out.println("Exist roles: ");
        for (RoleType roleType : values){
            System.out.println("-> " + roleType.name());
        }

        System.out.println("Enter user's login: ");
        String loginUser = scanner.nextLine();

        System.out.println("Enter role: ");
        String roleUser = scanner.nextLine();


        try {
            RoleType roleType = RoleType.valueOf(roleUser.toUpperCase());
            User user = userService.getByLogin(loginUser);
            userService.changeUserRole(user, roleType);

        }catch (IllegalArgumentException e){
            System.out.println("Incorrect type of role!");
        }catch (UserException e){
            System.out.println(e.getMessage());
        }finally {
            showMainMenu();
        }
    }

    /**
     * Метод для взаимодействия с администратором и показ списка аудита
     * */
    public void showAuditLog() {
        System.out.println("------- Menu: audit logs -------");
        Map<String, List<AuditLog>> allAuditLogs = auditLogService.getAllAuditLogs();

        for(Map.Entry<String, List<AuditLog>> auditLogs: allAuditLogs.entrySet()){
            System.out.println("-> audit logs for user: " + auditLogs.getKey());

            for (AuditLog auditlog : auditLogs.getValue()){
                System.out.println(auditlog);
            }
        }

    }
}