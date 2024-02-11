package by.anpoliakov.domain.entity;

import by.anpoliakov.domain.enums.RoleType;

import java.math.BigInteger;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleType=" + roleType +
                '}';
    }
}
