package by.anpoliakov.exception;

/**
 * Класс ошибки возникающей при работе с AuditLogServiceImpl
 */
public class AuditLogException extends RuntimeException {
    public AuditLogException(String message) {
        super(message);
    }
}
