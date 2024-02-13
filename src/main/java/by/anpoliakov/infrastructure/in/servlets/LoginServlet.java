package by.anpoliakov.infrastructure.in.servlets;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.exception.AuthenticationException;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.service.AuthenticationService;
import by.anpoliakov.service.impl.AuthenticationServiceImpl;
import by.anpoliakov.util.JwtTokenManager;
import by.anpoliakov.util.ValidatorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Сервлет для авторизации пользователя
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final AuthenticationService authService;

    public LoginServlet() {
        this.objectMapper = new ObjectMapper();
        this.authService = new AuthenticationServiceImpl(UserRepositoryImpl.getInstance());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");

            UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);
            ValidatorDto.validateDto(userDto);
            User user = authService.authorize(userDto);
            String token = JwtTokenManager.generateToken(user.getLogin());

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{" + "token:" + token + "}");
            out.flush();
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
