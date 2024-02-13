package by.anpoliakov.infrastructure.in.servlets;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.exception.AuthenticationException;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.service.AuthenticationService;
import by.anpoliakov.service.impl.AuthenticationServiceImpl;
import by.anpoliakov.util.ValidatorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;

import java.io.IOException;

/**
 * Сервлет для регистрации нового пользователя
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final AuthenticationService authService;

    public RegistrationServlet() {
        this.objectMapper = new ObjectMapper();
        this.authService = new AuthenticationServiceImpl(UserRepositoryImpl.getInstance());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);
            ValidatorDto.validateDto(userDto);
            authService.authorize(userDto);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (AuthenticationException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
