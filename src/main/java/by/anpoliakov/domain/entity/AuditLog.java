package by.anpoliakov.domain.entity;

import by.anpoliakov.domain.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Setter
@Getter
/** Класс представляет собой аудит действия пользователя */
public class AuditLog {
    private BigInteger id;
    private String loginUser;
    private LocalDateTime date;
    private ActionType actionType;

    public AuditLog(BigInteger id, String loginUser, LocalDateTime date, ActionType actionType) {
        this.id = id;
        this.loginUser = loginUser;
        this.date = date;
        this.actionType = actionType;
    }

    public AuditLog(String loginUser, LocalDateTime date, ActionType actionType) {
        this.loginUser = loginUser;
        this.date = date;
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "action: " + actionType + ", date: " + date;
    }
}
