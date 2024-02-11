package by.anpoliakov.infrastructure.in.filters;

import by.anpoliakov.util.JWTTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter doing operations with jwt token such like validation
 */
@WebFilter(urlPatterns = "/*")
public class JWTFilter extends HttpFilter {
    private final JWTTokenHandler jwtHandler;

    public JWTFilter() {
        jwtHandler = JWTTokenHandler.getInstance();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader("Authorization");

        if (!header.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            chain.doFilter(req, res);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jwtHandler.validate(token)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", jwtHandler.getUser(token));
        chain.doFilter(req, res);

    }
}
