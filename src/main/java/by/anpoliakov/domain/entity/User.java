package by.anpoliakov.domain.entity;

import by.anpoliakov.domain.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
/** Класс представляет собой пользователя приложения */
public class User {
    private BigInteger id;
    private String login;
    private String password;
    private RoleType roleType;

    public User(BigInteger id, String login, String password, RoleType roleType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleType = roleType;
    }

    public User(String login, String password, RoleType roleType) {
        this.login = login;
        this.password = password;
        this.roleType = roleType;
    }
}
