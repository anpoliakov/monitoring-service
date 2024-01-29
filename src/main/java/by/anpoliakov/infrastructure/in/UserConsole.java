package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entities.MetersReadings;
import by.anpoliakov.domain.entities.MeterType;
import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.interfaces.ConsoleInterface;
import by.anpoliakov.services.MeterTypeRegistry;
import by.anpoliakov.services.MetersReadingsService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UserConsole implements ConsoleInterface {
    private MetersReadingsService metersReadingsService;
    private MeterTypeRegistry meterTypeRegistry;
    private User currentUser;

    public UserConsole(User user) {
        currentUser = user;
        meterTypeRegistry = MeterTypeRegistry.getInstance();
        this.metersReadingsService = new MetersReadingsService(meterTypeRegistry);
    }

    /** Метод показа главного меню авторизированного пользователя */
    public void showMainMenu() {
        int choice = -1;

        do{
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
                case 0 -> {}
                default -> System.out.println("Incorrect menu selection, please try again:");
            }
        } while (choice != 0);
    }

    /** Метод для отображения последних показаний пользователя */
    private void showCurrentReadings() {
        MetersReadings lastMetersReadings = metersReadingsService.getLastMeterReadingsUser(currentUser);

        if(lastMetersReadings != null){
            System.out.println("The last meters readings: ");
            System.out.println("Date: " + lastMetersReadings.getDate());
            Map<MeterType, Integer> readings = lastMetersReadings.getReadings();
            for(Map.Entry entity : readings.entrySet()){
                System.out.println("Type meter: " + entity.getKey() + ", value: " + entity.getValue());
            }

            System.out.println("Press any key to continue...");
            new Scanner(System.in).nextLine();
        }else {
            System.out.println("Firstly, input the meter readings (number menu [2])!");
        }
    }

    private void inputReadings() {
        Scanner scanner = new Scanner(System.in);
        LocalDate nowDate = LocalDate.now();

        //если пользователь хочет довнести данные
        Optional<MetersReadings> optionalReadings = metersReadingsService.getMetersReadingsByUserAndDate(currentUser, nowDate);
        if(optionalReadings.isPresent()){
            MetersReadings metersReadings = optionalReadings.get();
            Map<MeterType, Integer> readings = metersReadings.getReadings();
            List<String> existTypesMeters = meterTypeRegistry.getNamesTypesMeters();

            List<String> collect = existTypesMeters.stream().
                    filter(type -> readings.containsKey(meterTypeRegistry.getMeterType(type))).
                    collect(Collectors.toList());

            for(String nameType : collect){
                int valueMeter = 0;

                while(true){
                    System.out.println("Enter value meter [" + nameType + "]: ");

                    try{
                        valueMeter = scanner.nextInt();
                        break;
                    }catch (InputMismatchException e){
                        System.out.println("Incorrect value, please try agan!");
                    }
                }

                metersReadingsService.addReading(currentUser, nameType, valueMeter, nowDate);
                System.out.println("ОК. Value [" + valueMeter + "], for meter readings [" + nameType + "] successfully recorded! ");
            }

            System.out.println("Сurrent month's readings are up to date!\nPress any key to continue...");
            scanner.nextLine();
        }else {
            //пользователь ещё не вносил ни каких показаний за текущий месяц
            //или только что создал аккаунт


        }
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
