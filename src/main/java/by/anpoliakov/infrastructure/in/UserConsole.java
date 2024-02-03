package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.domain.entity.MetersReadings;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.ActionType;
import by.anpoliakov.exception.MeterReadingException;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.infrastructure.ConsoleInterface;
import by.anpoliakov.service.AuditLogService;
import by.anpoliakov.service.MeterReadingService;
import by.anpoliakov.service.MeterTypeService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@AllArgsConstructor
public class UserConsole implements ConsoleInterface {
    private User currentUser;
    private MeterReadingService meterReadingService;
    private MeterTypeService meterTypeService;
    private AuditLogService auditLogService;

    /**
     * Метод показа главного меню авторизированного пользователя
     * */
    public void showMainMenu() {
        int choice = -1;

        while (choice != 0){
            System.out.println("----------- Menu USER -----------");
            System.out.println("[1] View current meter readings");
            System.out.println("[2] Input meter readings");
            System.out.println("[3] View readings for a specific month");
            System.out.println("[4] View history");
            System.out.println("[0] Exit");
            System.out.print("Enter the option selected:");

            switch (choice = getInputNumberMenu()){
                case 1 -> {
                    showCurrentReadings();}
                case 2 -> {
                    inputReadings();}
                case 3 -> {
                    showReadingsForSpecificMonth();}
                case 4 -> {
                    showHistory();}
                case 0 -> {
                    choice = 0;
                    auditLogService.addAuditLog(currentUser, ActionType.WORK_COMPLETION);
                }
                default -> System.out.println("Incorrect menu selection, please try again:");
            }
        }
    }

    /**
     * Метод для отображения последних показаний пользователя
     * */
    private void showCurrentReadings() {
        try {
            MetersReadings currentMetersReadings = meterReadingService.getLastMetersReadingsUser(currentUser.getLogin());

            System.out.println("The last meters readings: ");
            LocalDateTime date = currentMetersReadings.getDate();
            System.out.println("Day: " + date.getDayOfMonth() + ", Month: " + date.getMonth());
            Map<MeterType, Integer> readings = currentMetersReadings.getReadings();
            for(Map.Entry entity : readings.entrySet()){
                System.out.println("Type meter: " + entity.getKey() + ", value: " + entity.getValue());
            }

            System.out.println("Press any key to continue...");
            new Scanner(System.in).nextLine();
        }catch (MeterReadingException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }
    }

    private void inputReadings() {
        Scanner scanner = new Scanner(System.in);

        try{
            List<String> namesMetersTypes = meterTypeService.getNamesMetersTypes();
            System.out.println("------- Menu for enter meters readings ------- ");
            System.out.println("Existing meters types: ");
            for (String meter : namesMetersTypes){
                System.out.println("-> " + meter);
            }

            System.out.println("Enter name of meter: ");
            String nameMeter = scanner.nextLine();

            System.out.println("Enter reading of meter: ");
            int reading = scanner.nextInt();

            meterReadingService.addReading(currentUser.getLogin(), nameMeter, reading);
            auditLogService.addAuditLog(currentUser, ActionType.SUBMIT_READING_METER);
        }catch (MeterTypeException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }catch (MeterReadingException e){
            System.out.println(e.getMessage());
            inputReadings();
        }
    }

    private void showReadingsForSpecificMonth() {
        System.out.println("------- Search for meter readings by month and year -------");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter month: ");
        int month = scanner.nextInt();

        System.out.println("Enter year: ");
        int year = scanner.nextInt();

        try {
            MetersReadings metersReadings = meterReadingService.getMetersReadingsBySpecificMonth(currentUser.getLogin(), month, year);
            System.out.println(metersReadings);
        }catch (MeterReadingException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }
    }

    private void showHistory() {
        System.out.println("------- Your history of adding meters readings -------");
        try{
            List<MetersReadings> metersReadingsUser = meterReadingService.getMetersReadingsByUser(currentUser.getLogin());
            for (MetersReadings metersReadings : metersReadingsUser){
                System.out.println(metersReadings);
            }
            auditLogService.addAuditLog(currentUser, ActionType.METERS_READINGS_VIEW);
        }catch (MeterReadingException e){
            System.out.println(e.getMessage());
            showMainMenu();
        }
    }
}
