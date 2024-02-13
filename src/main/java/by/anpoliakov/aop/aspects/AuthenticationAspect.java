package by.anpoliakov.aop.aspects;

import by.anpoliakov.exception.TokenNotExist;
import by.anpoliakov.util.JwtTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AuthenticationAspect {

    @Around("execution(* by.anpoliakov.infrastructure.in.servlets.*.do*(..)) && args(request, response) && !execution(* by.anpoliakov.infrastructure.in.servlets.RegistrationServlet.doPost(..)) && !execution(* by.anpoliakov.infrastructure.in.servlets.LoginServlet.doPost(..))")
    public void isLoginUser(ProceedingJoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("Checking token ...");

        try{
            JwtTokenManager.validateToken(request);
            System.out.println("Токен есть! пускаем дальше");
            joinPoint.proceed();
        }catch (TokenNotExist e){
            System.out.println("Не вошёл в учётную запись!!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
