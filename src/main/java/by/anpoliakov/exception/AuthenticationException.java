package by.anpoliakov.exception;

/**
 * Класс ошибки возникающей при работе с AuditLogServiceImpl
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
