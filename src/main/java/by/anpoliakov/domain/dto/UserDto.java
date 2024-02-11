package by.anpoliakov.domain.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

public class UserDto {
    @JsonSetter("login")
    private String login;

    @JsonSetter("password")
    private String password;

    public UserDto() {
    }

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
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
}
