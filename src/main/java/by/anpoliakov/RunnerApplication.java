package by.anpoliakov;

import by.anpoliakov.infrastructure.in.MainConsole;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.services.AuthenticationService;

public class RunnerApplication {
    public static void main(String[] args) {
        MainConsole mainConsole = new MainConsole();
        mainConsole.showMainMenu();
    }
}
