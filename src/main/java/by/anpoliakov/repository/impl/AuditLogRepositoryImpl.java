package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.AuditLog;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.repository.AuditLogRepository;

import java.util.*;

public class AuditLogRepositoryImpl implements AuditLogRepository {
    private static AuditLogRepository instance;
    private Map<String, List<AuditLog>> auditLogs;

    private AuditLogRepositoryImpl() {
        auditLogs = new HashMap<>();
    }

    public static AuditLogRepository getInstance(){
        if (instance == null){
            instance = new AuditLogRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(AuditLog auditLog) {
        List <AuditLog> newAuditLogsList = null;
        Optional<List<AuditLog>> optionalAuditLogsList = getAuditLogsByUser(auditLog.getUser());

        if(optionalAuditLogsList.isEmpty()){
            newAuditLogsList = new ArrayList<>();
            newAuditLogsList.add(auditLog);
            auditLogs.put(auditLog.getUser().getLogin(), newAuditLogsList);
        }else {
            List<AuditLog> existAuditLogList = optionalAuditLogsList.get();
            existAuditLogList.add(auditLog);
        }
    }

    @Override
    public Map<String, List<AuditLog>> getAllAuditLogs() {
        return auditLogs;
    }

    @Override
    public Optional<List<AuditLog>> getAuditLogsByUser(User user) {
        return Optional.ofNullable(auditLogs.get(user.getLogin()));
    }
}
