package by.anpoliakov.infrastructure.in.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * Общий фильтр для всех сервлетов, настраивает кодировку и тип ответа
 */
@WebFilter(urlPatterns = "/*")
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        servletResponse.setContentType("application/json; charset=UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
