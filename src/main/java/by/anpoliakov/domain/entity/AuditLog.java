package by.anpoliakov.domain.entity;

import by.anpoliakov.domain.enums.ActionType;

import java.math.BigInteger;
import java.time.LocalDateTime;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
