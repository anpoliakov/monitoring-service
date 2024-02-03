package by.anpoliakov.exception;

/** Класс ошибки возникающей при работе с UserServiceImpl */
public class UserException extends RuntimeException{
    public UserException(String message) {
        super(message);
    }
}
