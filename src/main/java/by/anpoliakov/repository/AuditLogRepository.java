package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.AuditLog;

import java.util.List;
import java.util.Optional;

public interface AuditLogRepository {
    void create(AuditLog auditLog);

    Optional<List<AuditLog>> findAuditLogsByLogin(String loginUser);
}
