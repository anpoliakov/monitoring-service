package by.anpoliakov.domain.entity;

import by.anpoliakov.domain.enums.RoleType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
/** Класс представляет собой пользователя приложения */
public class User {
    private String name;
    private String login;
    private String password;
    private RoleType roleType;
}
