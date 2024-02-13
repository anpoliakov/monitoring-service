package by.anpoliakov.domain.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto implements ValidatableDTO {
    @NotBlank
    @Size(min = 5)
    @Size(max = 25)
    @JsonSetter("login")
    private String login;

    @NotBlank
    @Size(min = 5)
    @Size(max = 25)
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
