package by.anpoliakov.domain.entity;

import by.anpoliakov.domain.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
/** Класс представляет собой аудит действия пользовател */
public class AuditLog {
    private User user;
    private LocalDateTime date;
    private ActionType actionType;

    @Override
    public String toString() {
        return "action: " + actionType + ", date: " + date;
    }
}
