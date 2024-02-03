package by.anpoliakov.exception;

/** Класс ошибки возникающей при работе с MeterReadingServiceImpl */
public class MeterReadingException extends RuntimeException{
    public MeterReadingException(String message) {
        super(message);
    }
}
