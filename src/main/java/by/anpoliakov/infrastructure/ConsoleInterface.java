package by.anpoliakov.infrastructure;

import java.util.NoSuchElementException;
import java.util.Scanner;

/** Общий интерфейс для реализаций консолей пользователей */
public interface ConsoleInterface {
    void showMainMenu();

    /**
     * Метод для получения пользовательского ввода
     *
     * @return число int - номер выбранного меню */
    default int getInputNumberMenu(){
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

        return choice;
    }
}
