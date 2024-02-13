package by.anpoliakov.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Class for creating audit
 */
@Aspect
public class AuditAspect {

//    private final AuditLogService auditLogService = new AuditLogServiceImpl(AuditLogRepositoryImpl.getInstance());

    @Pointcut("within(@by.anpoliakov.aop.annotations.Audit *) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    /**
     * Aspect for creating audit
     */
    @Around("annotatedByAuditable()")
    public Object audit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
//        AuditLogDto dto = new AuditLogDto();
//        dto.setText("Action was performed on the entity by method: " + proceedingJoinPoint.getSignature().toShortString());
//        auditService.create(dto);
        return result;
    }
}
