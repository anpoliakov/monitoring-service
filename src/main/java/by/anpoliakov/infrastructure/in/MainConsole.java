package by.anpoliakov.infrastructure.in;

import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.enums.Role;
import by.anpoliakov.domain.interfaces.ConsoleInterface;
import by.anpoliakov.repository.UserRepository;
import by.anpoliakov.services.AuthenticationService;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainConsole implements ConsoleInterface {
    private AuthenticationService authService;
    private ConsoleInterface console;

    public MainConsole() {
        this.authService = new AuthenticationService();
    }

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

    /** Меню с авторизацией пользователя */
    public void showLoginMenu() {
        System.out.println("----------- Authorization -----------");
        System.out.println("(enter [0] to return to the main menu)");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your login: ");
        String login = scanner.nextLine().trim();
        if(login.equals("0")){
            return;
        }

        System.out.println("Enter your password: ");
        String password = scanner.nextLine().trim();
        if(password.equals("0")){
            return;
        }

        User user = authService.loginUser(login, password);
        if(user != null){
            switch (authService.getRoleCurrentUser(user)){
                case ADMIN -> {console = new AdminConsole(user);}
                default -> {console = new UserConsole(user);}
            }
            console.showMainMenu();
        }else {
            System.out.println("This user was not found! Try again!");
            showLoginMenu();
        }
    }

    /** Меню регистрации пользователя */
    public void showRegistrationMenu() {
        System.out.println("----------- Registration -----------");
        System.out.println("(enter [0] to return to the main menu)");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = scanner.nextLine().trim();
        if(name.equals("0")){
            return;
        }

        System.out.println("Enter your login: ");
        String login = scanner.nextLine().trim();
        if(login.equals("0")){
            return;
        }

        System.out.println("Enter your password: ");
        String password = scanner.nextLine().trim();
        if(password.equals("0")){
            return;
        }

        User user = authService.registerUser(name, login, password);
        if(user != null){
            console = new UserConsole(user);
            System.out.println("You are successfully registered! Your account: ");
            console.showMainMenu();
        }else {
            showRegistrationMenu();
        }
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

    //TODO: проверить что если ввели пустую строку? return null REFACTORING
    private String getInputUserData(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
}
