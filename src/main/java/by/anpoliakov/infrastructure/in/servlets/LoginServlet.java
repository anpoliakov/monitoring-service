package by.anpoliakov.infrastructure.in.servlets;

import by.anpoliakov.domain.dto.UserAuthenticationDTO;
import by.anpoliakov.repository.impl.UserRepositoryImpl;
import by.anpoliakov.service.AuthenticationService;
import by.anpoliakov.service.impl.AuthenticationServiceImpl;
import by.anpoliakov.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import javax.xml.bind.ValidationException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private ObjectMapper objectMapper;
    private AuthenticationService authService;
    private final Validator validator;
    private UserServiceImpl userService = new UserServiceImpl(UserRepositoryImpl.getInstance());

    public LoginServlet() {
        this.objectMapper = new ObjectMapper();
        this.authService = new AuthenticationServiceImpl(UserRepositoryImpl.getInstance());
        validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String requestBody = req.getReader().lines().collect(Collectors.joining());

        try {
            UnsecuredAuthenticationRequestDto unsecuredPlayerRequestDto =
                    mapper.validateValue(requestBody, UnsecuredAuthenticationRequestDto.class);
            AuthenticationRequest authenticationRequest =
                    authenticationRequestMapper.toAuthenticationRequest(unsecuredPlayerRequestDto);

            AuthenticationDto authenticatedPlayerDto = controller.authenticate(authenticationRequest);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Authorization", "Bearer " + produceJwt(authenticatedPlayerDto));
            resp.getOutputStream().write(mapper.writeValueAsBytes(authenticatedPlayerDto));
        } catch (BadCredentialsException | ConstraintViolationException e) {
            ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getOutputStream().write(mapper.writeValueAsBytes(exceptionDto));
        }
    }

}
