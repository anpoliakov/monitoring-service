package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entities.MeterType;
import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.enums.Role;
import by.anpoliakov.domain.interfaces.ConsoleInterface;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.services.MeterTypeRegistry;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AdminConsole implements ConsoleInterface {
    private UserRepository userRepo;
    private MeterTypeRegistry meterTypeRegistry;
    private User currentUser;

    public AdminConsole(User user) {
        this.currentUser = user;
        this.userRepo = UserRepositoryImpl.getInstance();
        meterTypeRegistry = new MeterTypeRegistry();
    }

    @Override
    public void showMainMenu() {
        int choice = -1;

        do{
            System.out.println("----------- Menu ADMIN -----------");
            System.out.println("[1] View the readings of all users");
            System.out.println("[2] Add type of Reading");
            System.out.println("[3] Change role of user");
            System.out.println("[0] Exit");
            System.out.print("Enter the option selected:");

            switch (choice = getInputNumberMenu()){
                case 1 -> {
//                    currentReadings();
                }
                case 2 -> {
                    addTypeReading();
                }
                case 3 -> {
                    changeRoleUser();
                }
                case 4 -> {
//                    showHistory();
                }
                case 0 -> {}
                default -> System.out.println("Incorrect menu selection, please try again:");
            }
        } while (choice != 0);
    }

    /** Метод добавления нового счётчика */
    public void addTypeReading(){
        List<String> namesTypesMeters = meterTypeRegistry.getNamesTypesMeters();

        System.out.println("Already exist types meters: ");
        for(String nameType : namesTypesMeters){
            System.out.println(">" + nameType);
        }

        System.out.println("Which type of meter you want to add? Enter please: ");
        Scanner scanner = new Scanner(System.in);
        String typeMeter = scanner.nextLine().trim();
        meterTypeRegistry.addMeterType(typeMeter);
        System.out.println("Success!");
    }

    /** Метод для изменения прав Пользователей на Администратора */
    public void changeRoleUser(){
        Map<String, User> users = userRepo.getAllUsers();
        System.out.println("List of users:");
        for(Map.Entry entry: users.entrySet()){
            System.out.println(entry.getKey());
        }

        System.out.println("Enter the user login to change the role to administrator: ");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine().trim();

        User user = userRepo.getByLogin(login);
        if(user != null){
            user.setRole(Role.ADMIN);
            System.out.println("User " + user.getLogin() + " is now an administrator ");
        }else {
            System.out.println("User is not found! ");
        }
    }

    /** Метод отвечающий за обработку пользовательского ввода номера меню */
    @Override
    public int getInputNumberMenu() {
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