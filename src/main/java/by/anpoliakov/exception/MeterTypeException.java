package by.anpoliakov.exception;

/**
 * Класс ошибки возникающей при работе с MeterTypeServiceImpl
 */
public class MeterTypeException extends RuntimeException {
    public MeterTypeException(String message) {
        super(message);
    }
}
