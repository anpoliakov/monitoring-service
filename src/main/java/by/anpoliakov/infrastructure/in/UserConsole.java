package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entities.MeterReading;
import by.anpoliakov.domain.entities.MeterType;
import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.interfaces.ConsoleInterface;
import by.anpoliakov.repository.impl.MeterTypeRegistry;
import by.anpoliakov.services.MeterReadingService;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserConsole implements ConsoleInterface {
    private MeterReadingService meterReadingService;
    private User currentUser;

    public UserConsole(User user) {
        currentUser = user;
        this.meterReadingService = new MeterReadingService(MeterTypeRegistry.getInstance());
    }

    /** Метод показа главного меню авторизированного пользователя */
    public void showMainMenu() {
        int choice = -1;

        do{
            System.out.println("----------- Menu USER -----------");
            System.out.println("[1] Current meter readings");
            System.out.println("[2] Input meter readings");
            System.out.println("[3] View readings for a specific month");
            System.out.println("[4] View history");
            System.out.println("[0] Exit");
            System.out.print("Enter the option selected:");

            switch (choice = getInputNumberMenu()){
                case 1 -> {
                    currentReadings();}
                case 2 -> {
                    inputReadings();}
                case 3 -> {
                    showReadingsForSpecificMonth();}
                case 4 -> {
                    showHistory();}
                case 0 -> {}
                default -> System.out.println("Incorrect menu selection, please try again:");
            }
        } while (choice != 0);
    }

    private void currentReadings() {
        MeterReading lastMetersReadings = meterReadingService.getLastMeterReadingsForUser(currentUser);
        if(lastMetersReadings != null){
            System.out.println("The last meters readings: ");
            System.out.println("Date: " + lastMetersReadings.getDate());
            Map<MeterType, Integer> readings = lastMetersReadings.getReadings();
            for(Map.Entry entity : readings.entrySet()){
                System.out.println("Type meter: " + entity.getKey() + ", value: " + entity.getValue());
            }
            new Scanner(System.in).nextLine();
        }else {
            System.out.println("Firstly, enter the meter readings!");
        }
    }

    private void inputReadings() {
        //ВНЕСЕНИЕ ДАННЫХ
        //проверка есть ли данные за текущий месяц - по всем типам счётчиков
        //если чего-то нет - даём возможность внести


    }

    private void showReadingsForSpecificMonth() {
        //просим ввести месяц и показываем данные по конкретному месяцу
    }

    private void showHistory() {
        //показываем всю историю показаний по текущему пользователю

    }

    /** Метод для получения пользовательского ввода
     * @return число int - номер выбранного меню */
    public int getInputNumberMenu(){
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        try {
            if (scanner.hasNextLine()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            }
        }catch (NoSuchElementException e){
            choice = -1;
        }

        //TODO: разобраться и закрыть Scanner
        return choice;
    }
}
