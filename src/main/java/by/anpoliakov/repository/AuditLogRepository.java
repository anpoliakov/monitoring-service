package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.AuditLog;
import by.anpoliakov.domain.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuditLogRepository {
    void add(AuditLog auditLog);
    Map<java.lang.String, List<AuditLog>> getAllAuditLogs();
    Optional<List<AuditLog>> getAuditLogsByUser(User user);
}
