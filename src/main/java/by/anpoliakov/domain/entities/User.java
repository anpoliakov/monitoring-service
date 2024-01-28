package by.anpoliakov.domain.entities;

import by.anpoliakov.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Setter
@Getter
@ToString
public class User {
    private String name;
    private String login;
    private String password;
    private Role role;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = Role.USER;
    }
}
